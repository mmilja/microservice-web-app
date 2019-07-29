#!/bin/bash

helm install --name=web-app-db stable/mongodb -f charts/mongo-chart/mongodb-values/values_minikube.yaml
