Get:
```bash 
xdg-open http://localhost:8080/api/stock/total-order-amount/4
```
Put: 
```bash
xdg-open http://localhost:8080/api/stock/item-per-stock/2?newCount=8000
```
Post: 
```bash                                                                                                                                                                                                                           
curl -X POST http://localhost:8080/api/stock/customer -H "Content-Type: application/json" -d '{"id": 88, "name": "Michael"}'                                                                                                      
``` 

Run app: 
```bash
mvn spring-boot:run
````
Clean run: 
```bash
mvn clean install
```

Test:
```bash
mvn test