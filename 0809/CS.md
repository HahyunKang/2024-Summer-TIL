## TCP/IP 소켓통신

### 소켓의 정의

소켓은 소프트웨어로 작성된 추상적인 개념의 접속점이라고 할 수 있는데, 소켓을 통하여 통신망으로 데이터를 송수신한다.

두 프로그램이 네트워크를 통해 서로 통신을 수행하도록 양쪽에 생성되는 링크의 단자이다. 두 소켓이 연결되면 서로 다른 프로세스끼리 데이터를 전달할 수 있다.

**TCP/IP 소켓 프로그래밍**

두 개의 시스템(또는 프로세스)이 소켓을 통해 네트워크 연결을 만들기 위해서는, 클라이언트 소켓이 IP 주소와 포트 번호를 통해 서버 소켓에게 연결을 시도하고, 서버 소켓은 어떤 연결 요청(포트 번호로 식별)을 받아들일지 미리 시스템에 등록하여 요청이 수신되었을 때 해당 요청을 처리한다.

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/2503eb61-3370-44dd-9aa2-fc838225e698/7072640f-3813-4115-965a-668a3da08267/Untitled.png)

### 서버 소켓

### **서버 소켓 바인딩 ( bind() )**

`bind()` API에 사용되는 인자는 소켓과 포트 번호(또는 IP 주소 + 포트 번호)이다. `bind()`는 소켓과 포트 번호를 결합하는 것이다.

시스템에는 수 많은 프로세스가 있는데, 그 중 네트워크 관련 프로세스가 TCP 또는 UDP 프로토콜을 사용한다면 각 소켓은 시스템이 관리하는 포트(0 ~ 65535) 중 하나의 포트 번호를 사용하게 된다. 여기서 서로 다른 소켓이 같은 포트 번호를 **중복으로** 사용하는 일이 생길 수 있기 때문에 운영체제는 내부적으로 포트 번호와 소켓 연결 정보를 관리한다. 그리고 `bind()` API는 해당 소켓이 지정된 포트 번호를 사용할 것이라는 것을 운영체제에 요청하는 역할을 한다. 만약 지정된 포트 번호를 다른 소켓이 사용하고 있다면 `bind()` API는 에러를 리턴한다

```kotlin
val server = ServerSocket().apply { bind(InetSocketAddress(port)) }
```

## **HTTP Request/Response 구조**

- http는 기본적으로 request/response 구조로 되어있다.
- 클라이언트와 서버의 모든 통신이 요청과 응답으로 이루어져 있다.

### Request Message

- Start Line
  - Http Method - get,post,put,delete 등이 존재
  - Request target -  HTTP Request가 전송되는 목표 주소
  - HTTP version - version에 따라 Request 메시지 구조나 데이터가 다를 수 있어서 version을 며시

    ```kotlin
    GET /test.html HTTP/1.1
    [HTTP Method] [Request target] [HTTP version]
    ```

- Headers
  - 해당 request에 대한 추가 정보(addtional information)를 담고 있는 부분
  - `Host` 요청하려는 서버 호스트 이름과 포트번호
  - `User-agent` 클라이언트 프로그램 정보. 이 정보를 통해 서버는 클라이언트 프로그램(브라우저)에 맞는 최적의 데이터를 보내줄 수 있다.
  - `Accept` 클라이언트가 처리 가능한 미디어 타입 종류 나열
- Body

  ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/2503eb61-3370-44dd-9aa2-fc838225e698/4c846d2c-03fd-4006-a4c4-dd683214609d/Untitled.png)

  - HTTP Request가 전송하는 데이터를 담고 있는 부분
  - 전송하는 데이터가 없다면 body 부분은 비어있다.

## Response Message

- **Status Line**

    ```kotlin
    HTTP/1.1 200 OK
    [HTTP version] [Status Code] [Status Text]
    ```

- **Headers**

  Request의 headers와 동일하다.

  다만 response에서만 사용되는 header 값들이 있다.

  예를 들어, User-Agent 대신에 Server 헤더가 사용된다.

- **Body - Request와 동일**

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/2503eb61-3370-44dd-9aa2-fc838225e698/7ad6ae2e-0588-4fe2-a80f-34a06b6f4d2a/Untitled.png)

## 유니캐스트,브로드캐스트,멀티캐스트

1. 유니캐스트
- 1:1 통신을 말하며, LAN 통신에서 송신자의 MAC과 수신자의 MAC주소를 알 때 메시지를 전달한다 .
- 개인적이거나 고유한 리소스가 필요한 모든 네트워크 프로세스에서 사용될 수 있다.
- 한 개의 목적지 MAC 주소를 사용하여 CPU 성능에는 문제를 주지 않는다.
1. 브로드캐스트
- 정보 전달 과정에서 송신자는 누군지 알지만, 수신자를 특정하지 않았을 때, 네트워크에 있는 모든 서버에게 정보를 알려야 할 때, 브로드캐스팅 방식을 사용한다.
- 브로드캐스팅용 주소가 따로 정해졍ㅆ고, 수신 받는 목적지는 이 주소가 오면 패킷을 자신의 cpu로 전송해서 패킷을 처리한다.
- 브로드캐스트 범위는 브로드 캐스트 도메인으로 제한된다..
1. 멀티캐스트
- 한 번의 송신으로 메시지나 정보를 목표한 여러 컴퓨터에 전송하는 것을 말하며, 1:다 전송 방법이다.
- 수신자를 그룹화하여 해당 그룹에 해당하는 수신자만 유니캐스트(그룹 특정) + 브로드캐스트(그룹 안에 있는 모든 수신자에게 정보 전달) 하는 것이다.