# Learn Essence of Spring Template

본 프로젝트는 Learn Essence of Spring 워크숍(강좌)에서 사용할 템플릿 프로젝트입니다.

---

## MovieBuddy

![MovieBuddyApplication](./moviebuddy.png)

여기 순수 자바로 작성된 콘솔 애플리케이션이 있습니다. 보시는 것처럼 그래픽 유저 인터페이스가 없습니다. 검은 바탕에 흰 글자로 구성된 콘솔 창에서 명령 프롬프트를 통해 명령어를 입력하면, 그에 해당하는 동작을 수행하고 결과를 텍스트로 출력합니다.

이 애플리케이션은 잘 동작하고 있지만, 코드에서 나쁜 악취가 많이 나고 있습니다. 이 와중에 몇 가지 기능을 더 추가해야 합니다. 워크숍(강좌) 과정에서 이 콘솔 애플리케이션에 스프링 프레임워크를 도입하고, 리팩토링을 통해 점진적으로 코드를 개선해볼 것입니다.

### 빌드 및 실행 방법
저장소를 복제하거나 압축 파일로 내려받은 받은 후 다음 명령어를 통해 애플리케이션을 빌드하고, 실행할 수 있습니다.
```
$❯ ./gradlew clean build
$❯ unzip build/distributions/moviebuddy.zip -d build/
$❯ build/moviebuddy/bin/moviebuddy
```

### 사용법
```
// 감독으로 영화 검색하기
❯ directedBy Michael Bay

// 개봉연도로 영화 검색하기
❯ releasedYearBy 2015

// 애플리케이션 종료하기
❯ quit
```

### 개발환경
- Java SE 11
- Gradle 6.6

---

워크숍(강좌)에 대한 자세한 소개는 [여기](https://springrunner.dev/training/learn-essence-of-spring-workshop/)에서 볼 수 있습니다.


---

#### 여기서 부터는 강의 내용을 정리한 부분입니다.

---


>일반적으로 자바프로그램은 프로그램의 시작점인 메인메서드가 시작되는 시점에
프로그램이 사용할 객체를 생성하고 생성된 객체에 있는 메서드를 호출하고
호출된 메서드에서 필요한 객체를 생성하는 것을 반복하는 방식으로 진행된다.
각 객체는 프로그램 흐름을 결정하거나 사용할 객체를 구성하는 작업에 능동적으로 참여한다.

---
### 제어의 역전

- 프로그램의 제어 흐름의 구조가 뒤바뀌는 것을 말한다 프레임워크나 서블릿 컨테이너 등에 적용된 개념이다.
- 제어의 역전 개념을 적용하면 설계가 깨끗하지고 유연성이 증가하며 확장성이 좋아진다.
- 특정 기술이나 환경에 종속되지 않은 보편적으로 사용되는 프로그래밍 모델이다.
- 스프링은 제어의 역전을 모든 기능의 기초가 되는 기반기술로 삼고 , 극한으로 활용한다.

---

### SOLID 원칙

- SOLID는 깔끔한 설계를 위해 적용 가능한 다섯가지 소프트웨어 설계 원칙이다.
- 함수와 데이터 구조를 클래스로 배치하는 방법 , 그리고 이들 클래스를 서로 결합하는 방법을 설명해주며
- 모듈고 컴포넌트 내부의 구조를 이해하기 쉽고 변경에 유연하게 만드는데 목적을 두고 있다.

1. OCP(Open Closed principal)
- 클래스나 모듈은 확장에는 열려있어야 하고 변경에는 닫혀있어야 한다.
* 해당 프로젝트에 MovieFinder 클래스는 MovieReader 인터페이스를 통해 메타데이터를 불러오는 기능을 제공받고 있으며
  MovieReader는 인터페이스로 구현되어 얼마든지 확장이 가능하도록 개방되어있다.
  반면 MovieFinder는 MovieReader의 변경에 영향받지 않고 폐쇄되어 있다.

2. DIP(dependency inversion principal)
- 상위 정책은 하위 정책에 의존하면 안되며 , 하위 정책이 상위 정책에 정의 추상타입에 의존해야한다.
* MovieFinder는 추상화된 MovieReader에만 의존하고 있으며  런타임시에 생성자를 통해 CsvMovieReader 객체를 전달받아
  동작하고 있다 . 때문에 다형성을 적극적으로 활용할 수 있으며 객체의 재사용성이 높아진다.

---
### 관심사 분리(Separation of Concerns)와 높은 응집도 , 낮은 결합도

- 관심이 같은 것끼리 한곳에 모으고 , 다른 것을 따로 떨어뜨려 서로 영향 주지 않도록 분리하는 것을 말한다.
- 응집도가 높다는 건 하난의 모듈, 클래스가 하나의 책임 또는 관심사에만 집중되어 있다는 뜻이다.
- 높은 응집도는 패키지 , 컴포넌트 , 모듈에 이르기까 대상의 크기가 달라도 동일한 원리고 적용될 수 있으며 계층화도 이 원리에 따라 적용된다.
- 결합도가 낮다는 건 하느의 오브젝트가 변경될 때에 관계를 맺고 있는 다른 오브젝트에 영향을 주지 않는다는 뜻이다.
- 결합도가 낮아지 변화에 대응하는 속도가 높아지고 구성이 깔끔하며 확장하기 편리하다.

### Strategy Pattern
- 개방 폐쇄 원칙 실현에 가장 적합한 패턴이다
- 자신의 기능 맥락에서 필요에 따라 변경이 필요한 알고리즘을 추상화를 통해 외부로 분리하고
  이를 구현하는 클래스들을 통해 필요에 다라 바꿔 사용할 수 있게하는 패턴을 말한다,
    * MovieReader 인터페이스를 통해 loadMovie()라는 로직이 추상화 되며 이를 구현한 csv , jaxb reader
      클래스가 Context의 필요에 따라 바꿔 사용되고 있다.이떄 MovieFinder는 전략패턴의 Context에 해당한

---

### 의존관계와 의존관계 주입(Dependency)

- MovieFinder는 코드 시점에선 MovieReader 인터페이스를 의존하지만
- 실행시점에선 CsvMovieReader 혹인 JaxbMovieReader를 의존한다.
- 이와 같이 코드 시점 의존 관계와 실행 시점의 의존관계가 다를 수 있다는 점은 다형성이 사용되는 중요한 이유이다 .
- 협력을 위해 필요한 의존관계는 유지하면 변경을 방해하는 의존 관계는 제거하는 것이 중요하며
- 이런 관점에서 객체지향 설계란 의존관계를 관리하는것이고 객체가 변경을 받아들일 수 있게 의존 관계를 정리하는 기술이라 할 수 있다.
- 외부의 다른 객체가 의존성이 필요한 클래스에가 필요한 의존성을 전달하는 방식을 의존 관계 주입이라한다.
- 외부에서 의존 관계 대상을 해결한 후 이를 사용하는 객체 쪽으로 주입한다.

---

#### 의존 관계 주입의 3가지 방식

- 생성자 주입(Constructor injection)  객체를 생성하는 시점에 생성자를 통해 의존 관계를 해결
- 설정자 주입(setter injection) setter 메서드를 통해 의존성을 주입
- 메소드 주입(method injection) 메서드 실행 시 인자를 이용한 의존관계를 주입.

---

### 스프링 IOC 컨테이너 와 빈(bean)

- 스프링은 제어의 역전 원칙에 따라 객채의 생성, 의존관계 주입과 같은 기능을 제공하는 Ioc 컨테이너로 bean factory를  제공하며 일반적으로는 bean factory를 확장한 ApplicationContext 객체를 사용한다.

- ApplicationContext은 빈팩토리의 특징을 그대로 가지고 있으면서 스프링 AOP 통합 , 국제화지원, 이벤트 기반 애플리케이션 , web 과 같은 기능을 제공한다.

- 스프링이 제거권을 가지고 직접 생성 , 의존관계를 주입하는 객체를 bean 이라하며 , 자바 빈즈 또는 엔터프라이즈 자바 빈즈에서 말하는 bean과 비슷한 객체 단위의 애플리케이션 컴포넌트를 말한다.

- ApplicationContext는 configuration metadata 라는 빈 구성 정보를 읽어 빈을 생성하고 관리하며 .  이를 바탕으로 애플리케이션을 구성한다.


---

### 스프링 IOC 컨테이너의 특징

- 컨테이너는 제어의 역전(inversion of control) 원리가 적용된 스프링 핵심 컴포넌트이다
- 컨테이너에 의해 생성 및 조립된 후 관리되는 객체를 빈이라 부른다.
- 빈 생성시 의존관계 주입(dependency injection)이 일어난다
- 빈 구성정보를 바탕으로 비지니스 오브젝트를 이용해 애플리 케이션을 구성하고 생애를 관리한다.

---

### bean 구성 정보 ( configuration metadata )란 ?

- 스프링 컨테이너가 빈 객체 생성 및 구성 , 조립시 사용하는 설정정보이다.
- 스프링은 빈구성정보를 읽고 내부적으로 bean definition 이라는 인터페이스로 추상화된 객체를 만들어 사용한다.
- 컨테이너 기능을 설정하거나 조정이 필요할 때도 사용된다.
- 자바 코틀린 그루비 XML 등 다양한 방식으로 작성할 수 있다.





