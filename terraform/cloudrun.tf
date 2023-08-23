resource "google_cloud_run_service" "iron-bank" {
  name     = "${var.app}"
  location = var.region
  
  metadata {
    labels = {
        app = var.app
    }
  }

  template {
    metadata {
      labels = {
          app = var.app
      }
      annotations = merge({
        "autoscaling.knative.dev/maxScale" = 5
        "autoscaling.knative.dev/minScale" = 0
      })
    } 
    spec {
      containers {
        image = var.iron_bank_docker_image
        env {
            name = "SPRING_DATASOURCE_URL"
            value_from {
              secret_key_ref {
                name = "iron-bank-jdbc-connection-string"
                key = "latest"
              }
            }
        }
        volume_mounts {
            name = "cockroach-cert"
            mount_path = "/root/.postgresql/"
        }
        ports {
            container_port = 8080
        }
        resources {
          limits = {
            cpu = "1000m"
            memory = "256Mi"
          }
        }
      }
      volumes {
        name = "cockroach-cert"
        secret {
            secret_name = "cockroach-cert"
            default_mode = "0444"
            items {
                key = "latest"
                path = "root.crt"
            }
        }
      }
    }
  }
}

data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_service_iam_policy" "noauth" {
  location    = google_cloud_run_service.iron-bank.location
  project     = google_cloud_run_service.iron-bank.project
  service     = google_cloud_run_service.iron-bank.name

  policy_data = data.google_iam_policy.noauth.policy_data

  depends_on   = [
    google_cloud_run_service.iron-bank
  ]
}