# TrainTicket Deployment for K8S with Istio
The repository contains changes to the microservices from [Train Ticket](https://fudanselab.github.io/research/MSFaultEmpiricalStudy/) with some fault injections. These microservices can be deployed in a Kubernetes cluster with Istio. We also added instrumentation with OpenTelemetry as well as logs with Fluentd, which both sends telemtry data to an OpenTelemetry collector that sends data to a Humio cloud account. The original license of the TrainTicket application is carried over. 

This repository also contains deployment files for the Train Ticket microservices from [Train Ticket](https://github.com/FudanSELab/train-ticket/), which are also made for the fault replicate. We have added instrumentation with an OpenTelemetry agent to both deployments. We have also added various deployment files for our Jaeger plugin, OpenTelemetry collector, and to configure Istio. These microservices are to be deployed in a Kubernetes cluster with Istio.

## Environment
To deploy this application use the files in the deployment-k8-istio folder, make sure that you have a running K8S cluster with Istio enabled.

You must also have a Humio cloud account, and set up a repository to receive spans from this application. You will have to add two secrets to the Kubernetes cluster:
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: humio-secrets
  namespace: istio-system
type: Opaque
data:
  # The API token associated with your Humio cloud account, used by Jaeger to query for spans.
  humio-api-token: base64...
  # The token associated with ingest on the repository in which you wish to store spans.
  humio-ingest-token-traces: base64...

```

These secrets should be added to the `istio-system` namespace after Istio has been set up in [Deployment](#Deployment).

## Deployment
First, create the `istio-system` namespace and operator by running:

```bash
kubectl apply -f istio-system.yaml
kubectl apply -f istio-operator.yaml
```

By now, you must add your secrets. Then deploy the monitoring infrastructure by executing:

```bash
kubectl apply -f otel-collector.yaml
kubectl apply -f jaeger.yaml
```

This will deploy an OpenTelemetry collector that sends spans to Humio, as well as a Jaeger application that can visualize traces stored inside Humio.

Before deploying the application, make sure to deploy the Java agent configuration first:

```bash
kubectl apply -f agent-config.yml
```

Lastly, deploy the TrainTicket application using either the files in TrainTicketFaults or TrainTicket:

```bash
kubectl apply -f <(istioctl kube-inject -f ts-deployment-part1.yml)
kubectl apply -f <(istioctl kube-inject -f ts-deployment-part2.yml)
kubectl apply -f <(istioctl kube-inject -f ts-deployment-part3.yml)
kubectl apply -f trainticket-gateway.yaml
```

## Testing
For testing, we installed prometheus using the prometheus-community Helm package. The admin login for the Grafana dashboard is:

- Username: admin
- Password: prom-operator
