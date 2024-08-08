## 동기 vs 비동기

동기는 ‘직렬적’으로 작동하는 방식이고, 비동기는 ‘병렬적’으로 작동하는 방식이다.

**동기**

- **직렬적으로** 태스크 수행
- 요청을 보낸 후, 으답을 받아야만 다음 동작이 아루어지는 방식. 태스크를 처리할 동안 나머지 태스크는 대기한다.
- 시스템의 효율이 저하될 수 있다

**비동기**

- **병렬적으로** 태스크 수행
- 요청을 보낸 후 응답의 수락 여부와는 상관없이 태스크가 동작하는 방식. 자원을 효율적으로 수행 가능
- 비동기 요청 시 응답 후 처리할  ‘**콜백함수**’를 알려준다. 태스크가 완료되었을 때, ‘**콜백 함수**’가 호출된다.

—> but, 콜백 패턴을 과도하게 사용하면 콜백 함수가 중첩되어 복잡도가 높아지는 **콜백 헬** 발생

```kotlin
step1(function (err, value1) {
    if (err) {
        console.log(err);
        return;
    }
    step2(function (err, value2) {
        if (err) {
            console.log(err);
            return;
        }
        step3(function (err, value3) {
            if (err) {
                console.log(err);
                return;
            }
            step4(function (err, value4) {
                // 정신 건강을 위해 생략
            });
        });
    });
});
```

## 멀티스레드

- 하나의 프로세스에서 여러 스레드로 자원을 공유하며, 태스크를 나누어 동시에 **병렬적**으로 수행한다.
- 장점:
  - `응답성` : 작업을 분리해서 수행하므로 **실시간으로 사용자에게 응답**
  - `효율성` : 속한 프로세스 내 스레드와 메모리, 자원을 공유하여 효율적으로 사용
  - `경제성` : 프로세스 생성 비용보다 스레드 생성 비용이 적고, context switching이 프로세스보다 빠름
- 단점 :
  - 하나의 프로세스 안에서 작동하므로, 하나의 스레드에서 문제가 생기면 전체 프로세스에 문제가 생긴다.

## 스레드풀

요청 마다 스레드를 생성하는 경우에, 스레드 생성에 소요되는 시간 때문에 요청 처리가 더 오래걸림.

처리 속도 보다 더 빠르게 요청이 늘어나면, 스레드가 계속 생성되고, 컨텍스트 스위칭이 더 자주 발생한다면, 오버헤드 증가로 cpu time이 낭비된다.

이를 해결하기 위해 스레드 풀이 등장한다.

스레드 풀은 미리 스레드를 여러 개 만들어놓고 재사용하는 방식으로 동작한다. 스레드 생성 시간이 절약하고, 빠르게 요청을 처리할 수 있다.

스레드 풀은, 여러 작업을 동시에 처리해야할 때 사용한다.

- task를 subtask로 나누어서 동시에 처리
- 순서 상관없이 동시 실행이 가능한 task 처리
- thread 하나마다 요청이 하나가 있는 모델

```kotlin
//newFixedThreadPool()로 스레드를 인자 수 만큼 만들고 작업을 수행할 때 재사용 가능한 스레드를 고
//르게 할 수 있다.
val service: ExecutorService = Executors.newFixedThreadPool(8)
```



## 코루틴

프로그램은 보통 **메인 루틴**과 **서브 루틴**으로 나뉜다.

- 메인 루틴 : 프로그램 전체의 개괄적인 동작 절차를 표시하도록 만들어진 루틴.
- 서브 루틴 : 반복적인 특정 기능을 모아서 별도로 묶인 루틴. → 함수와 비슷하다.

**코루틴**도, 루틴의 일종이지만, 메인-서브 개념이 없고, 진입점과 탈출점이 여러 개이다. 따라서 언제든지 서브루틴 실행 도중에 나갈 수 있고, 돌아올 수 있다.

- task 단위 = Object
  - 다수의 작업 각각에 object를 할당한다.
  - 이 object는 객체를 담는 JVM Heap에 적재된다.
- 프로그래머의 코딩을 통해 switching 시점을 마음대로 정하면서 동시성을 보장한다.
  - suspend(**non-blocking**) : 작업 1이 작업2의 결과가 나올 때까지 기다려야 한다면, 작업 1은 suspend 되지만, 작업 1을 수행하던 thread는 그대로 유효하기 때문에 작업2도 작업1과 동일한 thread에서 실행된다. 즉 OS-level의 context switching은 필요가 없다.




![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/2503eb61-3370-44dd-9aa2-fc838225e698/34cb9ff2-46c7-4f12-b1d0-cbc57126aebf/Untitled.png)

## 코루틴의 장점

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/2503eb61-3370-44dd-9aa2-fc838225e698/c85fe561-8345-45be-96e4-5ce2e5e6ad0f/Untitled.png)

1. **협력적 멀티태스킹**

일반함수(drawAnimal())을 호출하면 이 함수는 이제 코루틴 함수가 되고 drawAnimal()을 언제든지 진입하거나 탈출할 수 있게 된다. 그렇게 이 drawAnimal()이라는 코루틴 함수를 실행하다가 이 내부에서 suspend가 붙은 함수를 만나면 더이상 아래 코드를 실행하지 않고 코루틴 함수를 탈출한다. suspend가 붙은 함수를 실행하는 동안 main 스레드는 drawAnimal()이라는 코루틴 함수를 나와서 다른 코드를 실행한다. 다른 코드들을 실행하다가도 suspend 함수가 끝났으면 다시 코루틴 함수에 진입해서 남은 코드를 실행한다.

코루틴이 **스레드를 점유**하고 있는데, 스레드는 어떻게 다른 작업을 할까??

코루틴은 스레드의 **모든 리소스를 독점하지 않는다**. 코루틴이 실행되는 동안, 스레드는 필요에 따라 다른 작업을 수행할 수 있다.

**예를 들어, 코루틴이 비동기 I/O 작업을 기다리는 동안에는, I/O 스레드는 다른 작업을 계속 수행할 수 있다.**

1. **동시성 프로그램밍 지원**

스레드는 컨텍스트 스위칭할 때, 오버헤드 비용이 든다. 코루틴은 **경량스레드라**고 불리는데, 하나의 스레드에 코루틴은 여러 개 존재한다. 실행 중이던 하나의 코루틴이 중단되면, 현재 스레드에서 재개할 다른 코루틴을 찾는다. 이때, 다른 코루틴을 같은 스레드 내에서 찾는 것이기 때문에 **오버헤드가 발생하지 않는다.**

## RunBlocking

- runblocking이라는 이름은 runBlocking{} 내부의 모든 코루틴이 실행을 완료할 때까지 이 스레드를 실행하는 스레드가 호출 기간 동안 차단됨을 의미한다.
- 어플리케이션 최상위 레벨에서 이와 같이 사용되는 runBlocking을 종종 볼 수 있고, 실제 코드 내부에서는 거의 볼 수 없다. 스레드는 고가의 리소스이고 스레드를 차단하는 것은 비효율적이며, 바람직하지 않은 경우가 많다.
- 아래 시뮬레이터 코드에서도 delay를 걸어줌으로써, 메인 스레드를 blocking 시켜 event들이 잘 동작하도록 해주었다.

```kotlin
        runBlocking {
            val pos = Pos()
            pos.receive()

            val dashboard = Dashboard()
            val workManager = WorkManager()
            workManager.checkEvent(dashboard)
            val delivery = Delivery()

            workManager.postEvent { event, completed, index ->
                if (!completed) println("w${index}에서 ${event.id} : ${event.type.typeName} 물품 분류 시작")
                else println("w${index}에서 ${event.id} : ${event.type.typeName} 물품 분류 완료")
            }

            delivery.postDeliveryEvent { event, completed, num ->
                if (!completed) println("배달기사${num + 1}- ${event.customer}${event.type.typeName}(${event.id}) 배송 시작")
                else println("배달기사${num + 1}- ${event.customer}${event.type.typeName}(${event.id}) 물품 배송 완료")
            }

            dashboard.printDashboard()
            delay(800000)
        }
```

## BlockingQueue

- BlockingQueue**는** Java에서 제공하는 인터페이스로, 스레드 간의 안전하고 효율적인 데이터 공유를 위한 큐이다.
- 큐는 여러 스레드가 동시에 접근할 수 있도록 설계되었으며, 생산자-소비자 문제와 같은 동기화 문제를 해결하는 데 유용

### **주요 특징**

**스레드 안전:** BlockingQueue는 여러 스레드가 동시에 접근하더라도 데이터 무결성을 유지한다. 내부적으로 동기화 메커니즘을 사용하여 스레드 간의 충돌을 방지힌디.

**블로킹 동작:** 큐가 비어 있을 때 소비자가 데이터를 가져오려 하면, 큐가 비어있다고 블로킹된다. 반대로, 큐가 가득 차 있을 때 생산자가 데이터를 추가하려 하면, 큐가 가득 차 있다고 블로킹된다. 이로 인해 생산자와 소비자 간의 조화를 유지할 수 있다.

**다양한 구현:**

- **ArrayBlockingQueue:** 고정 크기의 배열을 사용하여 구현된 큐
- **LinkedBlockingQueue:** 링크드 리스트를 사용하여 구현된 큐로, 크기가 제한되지 않거나 지정된 크기를 가질 수 있다.
- **PriorityBlockingQueue:** 우선 순위 큐로, 요소의 자연 순서나 제공된 비교자(comparator)에 따라 정렬된다.