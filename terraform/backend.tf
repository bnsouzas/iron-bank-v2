terraform {
  backend "gcs" {
    bucket  = "finance-assistent-tf-state"
    prefix  = "iron-bank-v2"
  }
}