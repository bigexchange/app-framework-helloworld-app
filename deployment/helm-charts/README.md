## Introduction

This chart bootstraps BigID App deployment on a [Kubernetes](http://kubernetes.io) cluster using the [Helm](https://helm.sh) package manager.

## Installing the Chart

To install the chart with the release name `basic-demo-app`:

```$ helm install basic-demo-app . --namespace [NAMESPACE]```

## Upgrding the chart

```$ helm upgrade basic-demo-app . --namespace [NAMESPACE]```

## Uninstalling the Chart

```$ helm uninstall basic-demo-app --namespace [NAMESPACE]```

## Rollback the Chart

```$ helm rollback basic-demo-app [REVISION] --namespace [NAMESPACE]```

