# 🚀 Local CI/CD Setup using Jenkins, SonarQube, Spring Boot, Docker & ngrok

This repository demonstrates a **complete end-to-end CI/CD pipeline running locally**, triggered automatically on Git push, with code quality enforcement using SonarQube.

> 💡 **Key Idea:** You don’t need expensive cloud CI tools to *learn CI/CD properly*. With the right guidance and fundamentals, a fully functional pipeline can be built locally.

---

## 🧩 Architecture Overview

**Flow:**

```
Git Push (main branch)
        ↓
GitHub Webhook
        ↓
ngrok (exposes local Jenkins)
        ↓
Jenkins (Docker)
        ↓
Build + Test + Sonar Scan
        ↓
SonarQube Quality Gate
```

---

## 🛠 Tech Stack

| Component       | Purpose                                 |
| --------------- | --------------------------------------- |
| Spring Boot     | Demo backend application                |
| Jenkins         | CI/CD pipeline orchestration            |
| SonarQube       | Static code analysis & quality gate     |
| Docker          | Containerization of Jenkins & SonarQube |
| GitHub Webhooks | Auto-trigger pipeline on code push      |
| ngrok           | Expose local Jenkins to GitHub          |

---

## 📁 Project Structure

```
.
├── Jenkinsfile
├── Dockerfile (optional for app)
├── pom.xml
├── src/
└── README.md
```

---

## 🐳 Docker Setup

### 1️⃣ Jenkins (Docker)

```bash
docker run -d \
  --name jenkins \
  -p 8080:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts
```

Access Jenkins:

```
http://localhost:8080
```

---

### 2️⃣ SonarQube (Docker)

```bash
docker run -d \
  --name sonarqube \
  -p 9000:9000 \
  sonarqube:lts
```

Access SonarQube:

```
http://localhost:9000
```

Default credentials:

```
username: admin
password: admin
```

---

## 🔐 SonarQube Configuration

1. Create a **Sonar token**

    * SonarQube → My Account → Security → Generate Token

2. Jenkins → **Manage Jenkins → Credentials**

    * Kind: Secret Text
    * Value: Sonar Token
    * ID: `sonar-token`

3. Jenkins → **Manage Jenkins → Configure System**

    * SonarQube Servers

        * Name: `SonarQube`
        * Server URL:

            * `http://sonarqube:9000` (Docker network)
            * OR `http://localhost:9000`

---

## ⚙ Jenkins Pipeline (Jenkinsfile)

Pipeline includes:

* Git checkout
* Maven build & tests
* SonarQube scan
* Quality Gate enforcement

Quality Gate is configured to **FAIL the pipeline** when:

* Code coverage < **90%**

---

## 📊 SonarQube Quality Gate

Configured rules:

* Code Coverage ≥ 90%
* No critical/blocker issues

If Quality Gate is:

* ❌ FAILED → Jenkins pipeline fails
* ⏳ IN PROGRESS → Jenkins waits (timeout configured)

---

## 🔁 GitHub Webhook Setup

### Webhook URL

Since Jenkins runs locally, **ngrok** is used.

```bash
ngrok http 8080
```

Example URL:

```
https://<random>.ngrok-free.dev/github-webhook/
```

### GitHub Settings

* Repository → Settings → Webhooks → Add Webhook

| Field        | Value                          |
| ------------ | ------------------------------ |
| Payload URL  | ngrok URL + `/github-webhook/` |
| Content Type | application/json               |
| Events       | Push events                    |
| Secret       | (optional)                     |

---

## 🔐 Jenkins Job Configuration

* Enable: **GitHub hook trigger for GITScm polling**
* SCM: GitHub repository URL
* Branch: `main`

Now every `git push` → pipeline auto-triggers 🎯

---

## 🧪 Verification Checklist

✔ Jenkins accessible locally
✔ SonarQube accessible
✔ Sonar scan runs successfully
✔ Quality Gate enforced
✔ Pipeline auto-triggers on push
✔ Pipeline fails if coverage < 90%

---

## 📌 Key Learnings

* CI/CD fundamentals matter more than tools
* Local setup helps understand real failure scenarios
* Webhooks + quality gates simulate real-world pipelines
* Cloud tools become easy once basics are strong

---

## 📚 Final Note

This setup proves that **you can learn CI/CD deeply without external paid platforms**.
All you need is:

* clear concepts
* right guidance
* hands-on practice

Happy building 🚀
