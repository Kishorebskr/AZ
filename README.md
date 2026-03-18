# DevOps Challenge Solution — Team Portal

## Overview

This repository contains the full DevOps setup for the **Team Portal** application — a web app for managing team performance metrics built with::

- **Backend:** Spring Boot 3 + Kotlin, H2 in-memory DB, exposes REST API at `/api`
- **Frontend:** React 18 + TypeScript + Vite, served via Nginx

**DevOps tooling used:**
- **Kubernetes:** [Kind](https://kind.sigs.k8s.io/) (Kubernetes in Docker) for local cluster
- **CI/CD:** GitHub Actions
- **Registry:** GitHub Container Registry (GHCR)

> **Note on GitLab CI:** The task states GitLab CI is the preferred tool. Since this repo is hosted on GitHub, GitHub Actions was used. The pipeline structure maps 1:1 to GitLab CI stages and can be ported with minimal effort.

---

## Architecture

```
Internet
    │
    ▼
Ingress (nginx-ingress)
    ├──► / ──────► Frontend (React + Nginx, port 80)
    │                  └── proxies /api/* ──► Backend
    └──► /api ──► Backend (Spring Boot, port 8080)
                       └── H2 in-memory DB
```

---

## Environments

| Environment | Branch    | Namespace | Image Tag    | Replicas |
|-------------|-----------|-----------|--------------|----------|
| Dev         | `develop` | `dev`     | `latest`     | 1        |
| Prod        | `main`    | `prod`    | `stable`     | 2        |

- `develop` → deploys automatically to `dev` namespace on every push
- `main` → deploys automatically to `prod` namespace on every push

---

## Repository Structure

```
.
├── .github/
│   └── workflows/
│       └── ci-cd.yml            # GitHub Actions CI/CD pipeline
├── backend/
│   ├── Dockerfile               # Multi-stage: Gradle build → JRE runtime
│   └── src/...                  # Spring Boot / Kotlin source
├── frontend/
│   ├── Dockerfile               # Multi-stage: Node build → Nginx serve
│   ├── nginx.conf               # SPA routing + /api proxy config
│   └── src/...                  # React / TypeScript source
├── k8s/
│   ├── namespaces/
│   │   ├── dev.yaml
│   │   └── prod.yaml
│   ├── backend/                 # Dev: Deployment + Service
│   ├── frontend/                # Dev: Deployment + Service
│   ├── ingress.yaml             # Dev ingress (teamportal-dev.local)
│   └── prod/                    # Prod manifests (2 replicas, higher limits)
│       ├── backend/
│       ├── frontend/
│       └── ingress.yaml         # Prod ingress (teamportal.local)
└── README.md
```

---

## Local Setup with Kind

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
- [Kind](https://kind.sigs.k8s.io/docs/user/quick-start/#installation)
- [kubectl](https://kubernetes.io/docs/tasks/tools/)

### 1. Create Kind cluster

```bash
kind create cluster --name teamportal
```

### 2. Install nginx ingress controller

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s
```

### 3. Build and load images into Kind

```bash
docker build -t ghcr.io/YOUR_GITHUB_USERNAME/backend:latest ./backend
docker build -t ghcr.io/YOUR_GITHUB_USERNAME/frontend:latest ./frontend

kind load docker-image ghcr.io/YOUR_GITHUB_USERNAME/backend:latest --name teamportal
kind load docker-image ghcr.io/YOUR_GITHUB_USERNAME/frontend:latest --name teamportal
```

> Replace `YOUR_GITHUB_USERNAME` with your actual GitHub username — also update the image references in `k8s/backend/deployment.yaml` and `k8s/frontend/deployment.yaml`.

### 4. Deploy to dev

```bash
kubectl apply -f k8s/namespaces/dev.yaml
kubectl apply -f k8s/backend/
kubectl apply -f k8s/frontend/
kubectl apply -f k8s/ingress.yaml
```

### 5. Add hosts entries

```bash
echo "127.0.0.1 teamportal-dev.local" | sudo tee -a /etc/hosts
```

### 6. Access the app

Open [http://teamportal-dev.local](http://teamportal-dev.local)

The backend API is also accessible at [http://teamportal-dev.local/api](http://teamportal-dev.local/api)

### Deploy to prod (same cluster, prod namespace)

```bash
kubectl apply -f k8s/namespaces/prod.yaml
kubectl apply -f k8s/prod/backend/
kubectl apply -f k8s/prod/frontend/
kubectl apply -f k8s/prod/ingress.yaml

echo "127.0.0.1 teamportal.local" | sudo tee -a /etc/hosts
```

Access at [http://teamportal.local](http://teamportal.local)

---

## CI/CD Pipeline

```
backend-test ──┐
               ├──► build-and-push ──► deploy-dev   (develop branch only)
frontend-build─┘                  └──► deploy-prod  (main branch only)
```

| Stage            | What it does                                            |
|------------------|---------------------------------------------------------|
| `backend-test`   | Runs Gradle tests with caching                         |
| `frontend-build` | Runs `npm ci` + `npm run build` with caching           |
| `build-and-push` | Builds Docker images, tags, pushes to GHCR             |
| `deploy-dev`     | `kubectl apply` to `dev` namespace + rollout wait      |
| `deploy-prod`    | `kubectl apply` to `prod` namespace + rollout wait     |

### Image Tagging Strategy

| Branch    | Tags produced              |
|-----------|----------------------------|
| `develop` | `latest`, `dev-<sha>`      |
| `main`    | `stable`, `<sha>`          |

Every build is traceable by commit SHA. Prod always runs `:stable`.

### Required GitHub Secret

| Secret       | Description                                       |
|--------------|---------------------------------------------------|
| `KUBECONFIG` | Base64-encoded kubeconfig for the target cluster  |

Encode and add:
```bash
cat ~/.kube/config | base64
# Paste into: GitHub repo → Settings → Secrets and variables → Actions → New secret
```

---

## Assumptions

1. **H2 in-memory database** — The backend uses embedded H2. No persistent storage is needed. Data resets on pod restart — acceptable for this scope.
2. **Spring Boot Actuator** — Already present in `build.gradle.kts`. Used for readiness/liveness probes at `/actuator/health`.
3. **Single Kind cluster** — Both `dev` and `prod` run in the same local cluster, isolated by namespaces. In production these would be separate clusters.
4. **No TLS** — Ingress runs plain HTTP. TLS termination via cert-manager would be the next step.
5. **Public GHCR images** — Images are assumed public, or the cluster has pull credentials pre-configured.
6. **CORS** — Updated `CorsConfig.kt` to allow requests from both ingress hostnames (`teamportal-dev.local`, `teamportal.local`) in addition to localhost.

---

## Future Improvements

### High Priority

- **Helm charts** — Replace the duplicated `k8s/` and `k8s/prod/` raw manifests with a single Helm chart parameterised by `values-dev.yaml` / `values-prod.yaml`. Eliminates duplication and makes environment management cleaner.
- **GitOps with ArgoCD** — Replace push-based `kubectl apply` in CI with ArgoCD watching the repo. More reliable, auditable, and supports one-click rollback.
- **Persistent database** — Replace H2 with PostgreSQL backed by a PersistentVolumeClaim (or an external managed DB like RDS). H2 loses all data on pod restart.
- **Port to GitLab CI** — Straightforward: GitHub Actions jobs map directly to GitLab CI stages. Would be the first infrastructure change when moving to Azeti's GitLab.

### Security

- **Non-root containers** — Add `securityContext: runAsNonRoot: true` to all pod specs.
- **Image scanning** — Add Trivy or Grype step in CI to catch CVEs before pushing images.
- **Network policies** — Restrict pod-to-pod traffic so frontend can only reach backend, not other namespaces.
- **Kubernetes Secrets** — Move any app credentials from env vars to K8s Secrets or an external secrets manager (Vault, AWS Secrets Manager).

### Reliability & Observability

- **Horizontal Pod Autoscaler (HPA)** — Autoscale prod deployments based on CPU/memory.
- **Pod Disruption Budgets (PDB)** — Guarantee at least 1 replica stays running during rolling updates.
- **Monitoring** — Add Prometheus + Grafana. The backend already exposes `/actuator/prometheus` via Micrometer.
- **Centralised logging** — Ship container logs to Loki or an ELK stack.

### CI/CD

- **Smoke tests post-deploy** — Add a `curl` health check step after each deploy to verify the rollout succeeded end-to-end.
- **Environment promotion gates** — Require manual approval before promoting from dev to prod.
- **Dependency caching** — Gradle and npm caches already in place; add Docker layer caching for faster image builds.
