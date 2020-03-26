oc create deployment scoring --image quay.io/redhatdemo/2020-quarkus-scoring-server:latest
oc expose deployment/scoring --port 8080
oc expose service/scoring
