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

> @Configuration : 클래스가 컨테이너의 빈 구성정보로 사용될 목적임을 선언하는 애노테이션 <br/>
  @Bean: 컨테이너에서 등록할 사용할 빈 객체를 생성하고 구성 및 초기화를 선언하는 애노테이션 <br/>

---

### bean scope

- 스프링 컨테이너는 빈을 생성할 떄 단 하나의 빈을 만들지 아니면 빈이 요청될 때마다 새로운 빈을 생성할지를 결정하는  메커니즘을 가지고 있으며 이를 빈 스코프라 부른다.
- 하나의 빈 객체를 만들땐 singleton scope
- 빈이 요청될때마다 새로운 빈을 생성하는 것을 프로토 타입 스코프라고 한다.
- default는 싱글톤 스코프이다.
- spring web에서는 리퀘스트 , 세션 ,웹소캣, 애플리케이션 등 몇가지 빈스코프가 더 제공된다.
- singleton scope는 가장 기본적인 scope이며 스프링 컨테이너가 시작될때 생성되고 종료될때 소멸한다.

> @Scope : 빈에 대한 스코프 방식을 지정해 줄 수 있다 . 

---

### bean 구성 정보 조합

- 관심사가 같은 구성 정보끼리 모아 모듈화가 가능하다.

> @Import : 애노테이션을 선언한 클래스에서 필요한 구성정보 클래스를 import 해온다 </br>
 @ImportResource: xml로 정의해놓은 구성정보를 가져온다.  


---

### Component scan

- 컴포넌트를 찾아 자동으로 빈으로 등록하고 관리한다.

> @ComponentScan : 지정된 패키지 경로에서 @Component 같은 스테레오 타입의 어노테이션(@Controller , @Service , @Repository)이 붙은 클래스를 찾아 빈으로 등록하고 관리한다.</br>
> 패키지가 지정되지 않으면 @ComponentScan이 선언된 클래스를 기준으로 찾는다.
> basePackages옵션으로 컴포넌트 스캔 시작 패키지를 지정할 수 있다. 
  @Component : 해당 클래스가 ComponentScan의 대상이 되도록한다


---

### Auto Wiring

- 컴포넌트가 빈으로 등록될때 @Autowired를 확인하고 의존관계를 자동 주입한다.
- @Autowired를 생성자에 붙혀 사용하며 생성자가 하나일때는 생략 가능하다.
-  @Autowired는 기본적으로 생성자의 파라미터 타입을 기준으로 의존관계를 주입한다. 

#### bean 이름 지정
- @Component("bean이름")으로 해당 빈의 이름을 지정할 수 있다.
- 스프링 빈은 @Bean으로 직접 등록할시 메서드의 이름이 빈의 이름이 되고
- @Component등 컴포넌트 스캔에의해 생성될 때는 해당 클래스의 이름이 카멜케이스로 변환되어 빈의 이름이 된다

#### @Autowired  bean  지정 두가지 방법
- 만약 파라미터 타입이 인터페이스고 구현체가 여러개라면 파라미터 변수의 이름으로 의존관계를 주입하며
  변수명은 컴포넌트 선언시 정의한 bean의 이름과 매칭된다.
- @Quailfier를 이용해 주입 되어야 하는 빈을 지정해 줄수도 있다.

````java
    //bean 이름
    @Repository("csvReader")
    public class CsvMovieReader implements MovieReader {};
    //의존관계 주입시 파라미터 변수명과 빈이름이 매칭된다
    @Autowired
    public MovieFinder(MovieReader csvReader) {
        this.movieReader = Objects.requireNonNull(csvReader);
    }
    
    //@Qualifier 사용
    @Autowired
    public MovieFinder(@Qualifier("csvMovieReader") MovieReader csvReader) {
            this.movieReader = Objects.requireNonNull(csvReader);
    }
````

</br>

#### 각 레벨별 autowiring 특징

1. field level 
    - 필드에 직접 @Autowired 어노테이션을 달아주면 된다.
    - 외부에서 변경이 힘들어지기 떄문에 테스트가 힘들어진다 . 

</br>



---

</br>

### Spring Test

- 스프링의 포조 프로그래밍은 테스트를 손쉽게 작성할 수 있는 환경을 제공하고 
- 기술에 종속되지 않는 코드를 작성할 수 있게 해준다.
- 애플리케이션을 서버에 배치하거나 테스트 서버에 연결하지 않아

>@SpringJUnitConfig: @ExtendWith,@ContextConfiguration 어노테이션이 합쳐진 어노테이션 </br>
</br>
@ExtendWith(SpringExtension.class):  테스트 실행 전략을 확장할 떄 사용하는 애노테이션 </br>
*SpringExtension.class : 스프링 테스트에서 제공하는 Junit 지원 클래스 </br>
</br>
@ContextConfiguration : 테스트 컨텍스트의 빈구성정보를 지정하는 애노테이션 빈구성 정보 클래스를 classes옵션으로 넘겨주면 된다.</br>


---

</br>

### 엔터프라이즈 애플리케이션의 계층

</br>

<b>presentation </b>: 사용자와 소프트 웨어간 상호작용을 처리

v
</br> 

<b>domain </b> : 핵심 업무 논리를 처리하는 객체들로 구성되며 애플리케이션에서 가장 중요한 자산이다.
</br> 
v
</br> 
<b>persistance</b> : 파일 또는 데이터베이스 시스템 등과 데이터 송/수신 처리를 맡는다.

---
<br/>

### separated interface pattern

<br/>

- 추상화 인터페이스를 클라이언트에 속한 패키지에 포함하는 구조를 말한다 
- 추상화 인터페이스를 클라이언트로 포함시켜 도메인 패키지를 완벽하게 분리시킬 수 있다.
- 성격이 다른 추상화 인터페이스가 필요하다면 새로운 패키지를 추가하고 새로운 구현체를 만들면 상위 수준의 협력 관계를 재사용할 수 있다.

---
</br>

### 스프링의 객체 지향

- 애플리케이션 기능을 구현하기 위해 협력에 참여하는 객체들 사이의 상호작용
- 객체들은 협력에 참여하기 위해 역할을 부여 받고 역할에  적합한 책임을 수행한다.
- 스프링은 어떻게 객체가 설계되고 , 만들어지고 , 어떻게 관계를 맺어 사용되는지에 중점을 둔 프레임워크이다.
- 스프링을 사용만한다고 깨끗하고 유연한 코드가 저절로 만들어지지 않는다.

</br>

---

</br>

### Profile : 환경에 따른 bean 구성 
</br>
- 해당 프로젝트에서 MovieFinder는 파일로 부터 영화 목록을 불러오는 역할을 하는 movieReader를 통해 csv,xml 파일로 부터 목록을 불러올 수 있지만 , 둘중 하나만의 기능을 사용할 수 있다. 때문에 만약 두가지 모두 상황에 맞게 동작하게 하려면 csv  버전과 xml 버전으로 나눠 배포해야 할 것이다.
- 스프링 위와 같은 문제를 해결하기 위해 Profile을 통해 환경에 따른 bean 구성 기능을 제공한다
- 각 profile을 상수값으로 저장할 클래스를 만든다. 

</br>

> @Profile : 특정 환경에 빈이 동작하도록 profile을 지정한다. </br>
@ActiveProfile : 테스트 환경이 특정 profile으로 동작하도록 지정한다. </br>


</br>

#### 런타임 환경에서 프로파일을 설정

</br>

1. applicationContext에 setProfile() 메서드를 통해 지정한다 (프로파일을 지정한 후에 컨텍스트가 생성되도록 해야함) </br>


2. vm argument로 profile을 전달하여 실행 (intellij 기준)

 - edit configurations > modify options > add vm options > 아래 코드 추가
> -Dspring.profiles.active=프로파일 이름


</br>

--- 

### 스프링 로깅

</br>

- 스프링은 SLF4J와 Log4j 로깅 프레임워크를 기본  라이브러리로 사용한다
- slf4j는 추상 로깅 프레임워크이기 떄문에 단독으로는 로깅이 불가능 하다.







</br>





