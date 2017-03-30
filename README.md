# NginxConfig
add/remove upstream and reload nginx
## Premise
nginx whith nginx_upstream_check_module is running

## Default
default nginx location: "/usr/local/nginx/"

## Note
nginx.conf location: "conf/nginx.conf"  

this shell of nginx config reload: "sbin/nginx -s reload"

**ps. nginx.conf and shell relative to nginx location**

location that the parameters of the constructor is nginx installation path

## Upstream
    upstream win-test-index {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "GET /index HTTP/1.0\r\n\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }

    upstream win-test-api {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "GET /myAPI-g?id=13 HTTP/1.0\r\n\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }

    upstream win-test-api-post {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "POST /myAPI-p HTTP/1.0\r\nContent-Length: 5\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\nid=27\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }

    upstream win-test-api-json {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "POST /myAPI-json HTTP/1.0\r\nContent-Length: 31\r\nContent-Type: application/json;charset=utf-8\r\n\r\n{\"id\":27,\"name\":\"Win\",\"age\":25}\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }

    upstream win-test-api-put-json {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "PUT /myAPI-put-j HTTP/1.0\r\nContent-Length: 31\r\nContent-Type: application/json;charset=utf-8\r\n\r\n{\"id\":27,\"name\":\"Win\",\"age\":25}\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }

    upstream win-test-api-delete-json {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "DELETE /myAPI-delete-j HTTP/1.0\r\nContent-Length: 31\r\nContent-Type: application/json;charset=utf-8\r\n\r\n{\"id\":27,\"name\":\"Win\",\"age\":25}\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }

    upstream win-test-api-put {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "PUT /myAPI-put HTTP/1.0\r\nContent-Length: 14\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\nname=put-param\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }

    upstream win-test-api-delete {
        # simple round-robin
        server 172.16.61.97:8087;
	      check_http_send "DELETE /myAPI-delete HTTP/1.0\r\nContent-Length: 14\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\nname=del-param\r\n";
        check interval=3000 rise=2 fall=5 timeout=1000 type=http;
    }
