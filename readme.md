# Simple Web Server
![Coverage](.github/badges/jacoco.svg)

![Branches](.github/badges/branches.svg)

A simplex HTTP 1.0 Server implemented in Java for educational
purposes based on W3C specifications (http://www.w3.org/Protocols/):

* [W3](https://www.w3.org/Protocols/HTTP/AsImplemented.html) Hypertext Transfer Protocol -- HTTP/0.9
* [RFC 1945](http://www.ietf.org/rfc/rfc1945.txt) Hypertext Transfer Protocol -- HTTP/1.0
* [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt) Hypertext Transfer Protocol -- HTTP/1.1
* [RFC 2617](http://www.ietf.org/rfc/rfc2617.txt) HTTP Authentication: Basic and Digest Access Authentication
* [RFC 6265](http://tools.ietf.org/html/rfc6265) HTTP State Management Mechanism (Cookies)

## Build
```
./gradlew jar 
```

## Run
```
java -cp build/libs/simple-web-server-1.0.jar liteweb.Server
```

## Performance test
```
bzt performance.yml
```

## After threaded
```
08:40:59 INFO: Request label stats:
+---------------------------------------------------------+--------+---------+--------+-------+
| label                                                   | status |    succ | avg_rt | error |
+---------------------------------------------------------+--------+---------+--------+-------+
| http://127.0.0.1:8080                                   |   OK   | 100.00% |  0.105 |       |
| http://127.0.0.1:8080/performance.yml                   |   OK   | 100.00% |  0.105 |       |
| http://127.0.0.1:8080/readme.md                         |   OK   | 100.00% |  0.105 |       |
| http://127.0.0.1:8080/src/main/java/liteweb/Server.java |   OK   | 100.00% |  0.105 |       |
+---------------------------------------------------------+--------+---------+--------+-------+
08:40:59 INFO: Test duration: 0:01:15
08:40:59 INFO: Samples count: 37050, 0.00% failures
08:40:59 INFO: Average times: total 0.105, latency 0.105, connect 0.000
```

## After LRU cache
```
10:02:05 INFO: Test duration: 0:01:21
10:02:05 INFO: Samples count: 28866, 0.00% failures
10:02:05 INFO: Average times: total 0.135, latency 0.135, connect 0.001
10:02:05 INFO: Percentiles:
+---------------+---------------+
| Percentile, % | Resp. Time, s |
+---------------+---------------+
|           0.0 |         0.003 |
|          50.0 |         0.117 |
|          90.0 |         0.166 |
|          95.0 |         0.291 |
|          99.0 |          0.66 |
|          99.9 |         1.403 |
|         100.0 |          1.93 |
+---------------+---------------+
10:02:05 INFO: Request label stats:
+---------------------------------------------------------+--------+---------+--------+-------+
| label                                                   | status |    succ | avg_rt | error |
+---------------------------------------------------------+--------+---------+--------+-------+
| http://127.0.0.1:8080                                   |   OK   | 100.00% |  0.135 |       |
| http://127.0.0.1:8080/performance.yml                   |   OK   | 100.00% |  0.137 |       |
| http://127.0.0.1:8080/readme.md                         |   OK   | 100.00% |  0.135 |       |
| http://127.0.0.1:8080/src/main/java/liteweb/Server.java |   OK   | 100.00% |  0.134 |       |
+---------------------------------------------------------+--------+---------+--------+-------+
```
