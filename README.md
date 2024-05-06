# 일본어 번역기 프로젝트

1. TDD 로 개발
2. TestContainer 로 통합 테스트 진행
3. Feign Client 로 외부 api 와 통신
4. redis 로 chache 사용
5. rabbitMQ 로 데이터 처리

## TDD 개발


## TestContainer 를 사용한 이유

테스트 컨테이너 환경의 장점은 다음과 같습니다.

자바 언어만으로 docker container 구성이 가능하며,
도커를 이용하여 테스트할 때 컨테이너를직접 관리해야 하는 번거로움을 해결 해주고, 운영환경과 유사한 스펙으로 테스트가 가능하다.
즉, 테스트 코드가 실행 될 때 자동으로 도커 컨테이너를 실행하여 테스트 하고, 테스트가 끝나면 자동으로 컨테이너를 종료 및 정리해준다.

여러가지 테스트 환경중 테스트 컨테이너를 활용한 이유의 대한 자세한 정리는 아래 링크 첨부 하겠습니다.

https://velog.io/@asdcz11/%EC%99%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88-%EC%9D%BC%EA%B9%8C

## Feign Client 사용 이유
기존에는 RestTemplate 만 사용해 왔지만, Feign Client 를 사용하면 RestTemplate 를 사용할 때 보다 더욱 간결하게 외부 api 통신 코드를 작성 할 수 있습니다.
아래는 기존 RestTemplate 코드에서 FeignClient 로 변경했을때의 코드의 비교 입니다.

- Feign Client
<img width="599" alt="스크린샷 2024-05-05 오후 9 48 36" src="https://github.com/ByeonJuHwan/translator/assets/105885581/555df13c-dc23-4905-8141-c83b150ee755">

- RestTemplate
<img width="846" alt="스크린샷 2024-05-05 오후 9 49 47" src="https://github.com/ByeonJuHwan/translator/assets/105885581/ef2e8ddd-2725-4ccb-a5eb-1e32a3180a9a">

Header 에 대한 처리를 인테페이스에 위임하면서 코드의 간결성이 증가했습니다.

## Redis 로 데이터 캐싱

## rabbitMQ 로 데이터 비동기 처리

비즈니스 로직을 처리함에 있어 동기적으로 처리하기 보다는 비동기적으로 구성하면서 따라오는 장점이 많다고 생각해 RabbitMQ를 사용했습니다.
비동기 통신을 통해 서비스간 의존성을 줄이고, 느슨한 결합을 유지하게 설계했습니다.

rabbitMQ 에 대한 기본적인 내용입니다. 

https://velog.io/@asdcz11/RabbitMQ-%EC%82%AC%EC%9A%A9%EA%B8%B0
