#Get all meals
curl "http://localhost:8080/topjava/rest/meals/" 

#Get meals with id = 100005
curl "http://localhost:8080/topjava/rest/meals/100005" 

#Get by filter
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=09:00:00&endDate=2020-01-30&endTime=11:00:00"

#Get by filter with null parameters
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=&startTime=&endDate=&endTime="

#Delete meal with id = 100005
curl -X DELETE "http://localhost:8080/topjava/rest/meals/100005" 

#Update meal with id = 100003
curl -H "Content-Type: application/json; charset=Windows-1251" -X PUT http://localhost:8080/topjava/rest/meals/100003 -d "{\"dateTime\":\"2022-11-11T07:00\",\"description\":\"Meal\",\"calories\":111}"    

#Create meal
curl -H "Content-Type:application/json; charset=Windows-1251" -X POST http://localhost:8080/topjava/rest/meals -d "{\"dateTime\":\"2022-10-11T12:00\",\"description\":\"ЗАвтрак\",\"calories\":333}" 