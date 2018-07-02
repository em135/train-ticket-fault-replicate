#Setup System.
1. Do the following instruction at 10.141.211.160
   mvn clean package
   docker-compose build
   Do tag instruction in [Docker Tag&Upload]
   Do push instruction in [Docker Tag&Upload]
2. Deploy file part1, part2, part3 at K8S cluster 10.141.212.22

#Fault Reproduce:
1. Admin Login 
2. Lock shanghai and nanjing
3. Login 
4. Cancel ticket. (Open chrome and see the network console)
5. Sometimes you will receive nothing.
6. You will see the log like exception.PNG.
7. Find out why.

#Tips
1. The info in ts-order-other-service/getStatusDescription may be helpful. Of course may be not.
2. You may need the Zipkin Span Log in OrderOtherApplication to help you. Or may be not.