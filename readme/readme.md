#Setup System.
1. Do the following instruction at 10.141.211.160
   mvn clean package
   docker-compose build
   Do tag instruction in [Docker Tag&Upload]
   Do push instruction in [Docker Tag&Upload]
   docker tag ts/ts-ui-dashboard 10.141.211.160:5000/f12/cluster-ts-ui-dashboard-debug-elk
   docker push 10.141.211.160:5000/f12/cluster-ts-ui-dashboard-debug-elk
2. Deploy file part1, part2, part3, part4 at K8S cluster 10.141.212.22
kubectl create -f ts-deployment-part1.yml
kubectl create -f ts-deployment-part2.yml
kubectl create -f ts-deployment-part3.yml
kubectl create -f ts-deployment-part4.yml

kubectl delete -f ts-deployment-part4.yml
kubectl delete -f ts-deployment-part3.yml
kubectl delete -f ts-deployment-part2.yml
kubectl delete -f ts-deployment-part1.yml
#Fault Reproduce:
1. Admin Login docker ps -a
2. Lock shanghai and nanjing
3. Login 
4. Cancel ticket. (Open chrome and see the network console)
5. Sometimes you will receive nothing.
6. You will see the log like exception.PNG.
7. Find out why.

#Tips
1. The info in ts-order-other-service/getStatusDescription may be helpful. Of course may be not.
2. You may need the Zipkin Span Log in OrderOtherApplication to help you. Or may be not.