# Comandos completos - LAB01 Microservicios

## 1. Prueba local (sin Docker, con WebClient y localhost)

```bash
# Terminal 1 - ms-productos en puerto 8081
cd ms-productos
mvn spring-boot:run

# Terminal 2 - ms-pedidos en puerto 8082
cd ms-pedidos
mvn spring-boot:run

# Verificar ms-productos
curl http://localhost:8081/api/productos

# Crear pedido (ms-pedidos llama a ms-productos via WebClient)
curl -X POST http://localhost:8082/api/pedidos \
  -H "Content-Type: application/json" \
  -d "{\"productId\": 1, \"quantity\": 2}"
```

## 2. Demostrar puertos diferentes (requisito del lab)

```bash
# ms-productos en puerto 9001
cd ms-productos
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9001

# ms-pedidos en puerto 9002, apuntando al nuevo puerto de productos
cd ms-pedidos
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9002 --ms.productos.url=http://localhost:9001/api/productos"
```

## 3. Build de imágenes Docker

```bash
# Construir imagen ms-productos
cd ms-productos
docker build -t luisrivas3/ms-productos:latest .

# Construir imagen ms-pedidos
cd ms-pedidos
docker build -t luisrivas3/ms-pedidos:latest .
```

## 4. Publicar en Docker Hub

```bash
docker login
docker push luisrivas3/ms-productos:latest
docker push luisrivas3/ms-pedidos:latest
```

## 5. Kubernetes con Minikube

```bash
# Iniciar Minikube
minikube start

# Verificar que está corriendo
kubectl cluster-info
kubectl get nodes

# Aplicar todos los manifiestos
kubectl apply -f k8s/ms-productos-deployment.yaml
kubectl apply -f k8s/ms-productos-service.yaml
kubectl apply -f k8s/ms-pedidos-deployment.yaml
kubectl apply -f k8s/ms-pedidos-service.yaml

# Verificar deployments y pods
kubectl get deployments
kubectl get pods
kubectl get services

# Ver logs de cada microservicio
kubectl logs deployment/ms-productos
kubectl logs deployment/ms-pedidos

# Exponer servicios en Minikube (LoadBalancer)
minikube tunnel

# En otra terminal, ver las IPs asignadas
kubectl get services

# Probar desde la IP externa asignada por minikube tunnel
curl http://<EXTERNAL-IP>:8081/api/productos
curl -X POST http://<EXTERNAL-IP>:8082/api/pedidos \
  -H "Content-Type: application/json" \
  -d "{\"productId\": 1, \"quantity\": 2}"

# Describir un pod (útil para troubleshooting)
kubectl describe pod <nombre-del-pod>

# Eliminar todos los recursos
kubectl delete -f k8s/
```

## 6. Verificar comunicación dentro del cluster

```bash
# Ver que ms-pedidos resuelve ms-productos-service por DNS interno
kubectl exec -it deployment/ms-pedidos -- wget -qO- http://ms-productos-service:8081/api/productos
```
