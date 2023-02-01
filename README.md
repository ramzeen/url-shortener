## Intro
A URL Shortener that provides api endpoints to shorten a given URL and expand the shortened URL to the original URL

## Build and run
Requires java 17

```console
mvn clean install
java -jar target/url-shortener-1.0.jar 
```

## Docker

After the artifact has been built:
```console
docker build -t url-shortener .
docker-compose up
```

## Swagger UI
http://localhost:8080/swagger-ui.html

## Open API Docs
http://localhost:8080/v3/api-docs

## Response to shorten a url
```console
curl -s 'localhost:8080/api/v1/create' -d '{"longURL":"https://www.cnn.com/2023/02/01/us/washington-hinman-glacier-disappear-climate/index.html", "userId":"abcdefgh"}' -H 'Content-Type: application/json
```
```javascript
{
  "longURL": "https://www.cnn.com/2023/02/01/us/washington-hinman-glacier-disappear-climate/index.html",
  "userId": "abcdefgh",
  "created": "2023-02-01-02 13:12:19",
  "expiration": "2024-02-01-02 13:12:19",
  "shortURL": "https://www.shorturl.com/fA6nvDqxL"
}
```

## Response to expand a valid short url
```console
curl -v 'http://localhost:8080/fA6nvDqxL'
```
```console
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /fA6nvDqxL HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.85.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 302
< Location: https://www.cnn.com/2023/02/01/us/washington-hinman-glacier-disappear-climate/index.html
< Content-Language: en-US
< Content-Length: 0
< Date: Wed, 01 Feb 2023 18:13:56 GMT
<
* Connection #0 to host localhost left intact
```
## Response to expand an invalid short url
```console
curl -v 'http://localhost:8080/abcd'
```

```console
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /abcd HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.85.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 404
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 01 Feb 2023 18:15:42 GMT
<
* Connection #0 to host localhost left intact
{"timestamp":"2023-02-01T18:15:42.056+00:00","status":404,"error":"Not Found","path":"/abcd"}
```
## Representation of shorted url

## Data Model in the db


## Capacity/traffic analysis
* Number of requests to shorten the url a day 10 million (~100 qps)
* Redirects a day (assuming 100 reads per a shortened url) - 10K qps
* Total urls to store (assuming a time period of 15 years) 10 million * 365 * 15 = 55 Billion
* Average size of a record - 1200 Bytes
* Total size  - 55B * 1200 = 65 TB

## Caching

## High Level Architecture

## Other Issues
