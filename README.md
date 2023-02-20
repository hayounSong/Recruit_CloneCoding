> ## 구현 화면

<p>

  ### 메인 화면
![](https://velog.velcdn.com/images/hayounsong/post/f3d7e53e-93e6-488f-b359-c96cac749563/image.png)
  ### 지원 화면
![](https://velog.velcdn.com/images/hayounsong/post/d1a93758-4c84-4003-8498-c2ba34c1ec37/image.png)

### 파트별 지원자 현황 확인
  <img src=""/>

![](https://velog.velcdn.com/images/hayounsong/post/4af8419d-60ca-4f00-bf3f-46dd852cafd9/image.png)



 </p>
 
 

> ## 개발 개요 및 비즈니스 요구사항 분석

### 개발개요

최근에 OB 지원을 앞둔 SOPT 자소서를 쓰던 와중에, 우연히 화면에 아주 잠깐 나오게 된 에러메시지를 보게 되었다. 바로 내가 백엔드던, 프론트엔드던, 항상 나를 힘들게 했던 CORS 에러였다. 여하튼 우연히 이 반가운 에러를 보던 와중, 기존에 내가 백엔드 프로젝트나 공부를 해보면서 느낀건, 클론 코딩이나 무엇을 목표로 만들어보는 것이 나의 실력향상에 크게 도움이 되는 것을 느꼈다. 그래서, 무작정 자소서를 쓰던 와중에 시간을 쏟아부어 동아리 리크루팅 사이트를 클론코딩 해보고 싶다는 생각이 강하게 들었다.

### 비즈니스 요구사항 분석

- OB로 지원하고 있는 동아리(SOPT)는, 6가지의 파트를 가지고 있다. 

- 지원하려는 applicant는 지원하고자 하는 파트를 무조건 1개 이상의 파트에 지원해야한다.
- 항상 많은 지원자수가 있는 동아리이기에, 파트별로 지원자수를 확인할 수 있는 API 혹은 뷰가 필요하다.
- 한명의 지원자는 반드시 한개의 파트에만 지원할 수 있다.


+.. 원래는 React로 프론트엔드 구조를 만든 후, REST API를 이용해 API 통신을 하고자 했으나, 도저히 프론트엔드까지 개발할 시간이 나지 않아, template 엔진을 이용하여 개발하였다.

> ## 사용 기술 스택 및 데이터베이스 구조

### 사용 기술 스택
<div>
<img alt="RED" src ="https://img.shields.io/badge/JAVA-004027.svg?&style=for-the-badge&logo=Jameson&logoColor=white"/>
<img alt="RED" src ="https://img.shields.io/badge/SPRING-6DB33F.svg?&style=for-the-badge&logo=Spring&logoColor=white"/>
<img alt="RED" src ="https://img.shields.io/badge/SPRING Boot-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logoColor=white"/>
<img alt="RED" src ="https://img.shields.io/badge/MariaDB-003545.svg?&style=for-the-badge&logo=MariaDb&logoColor=white"/>
<img alt="RED" src ="https://img.shields.io/badge/Amazon Rds-527FFF.svg?&style=for-the-badge&logo=AmazonRds&logoColor=white"/>
  </div>
  
 사용 기술은, 당연히 Spring Framework를 사용했으며, 특이사항으로는 MariaDB를 사용했고, 이를 위한 AWS RDS까지 사용하여 프로젝트를  진행하였다.

### 데이터베이스 설계

![](https://velog.velcdn.com/images/hayounsong/post/a9e904c1-eb7a-438e-a192-e65b24883259/image.png)

 ERD Cloud를 이용하여 데이터베이스를 설계하였다. 휴학 여부를 나타내는 속성인 isabsense는 enum 타입으로 만들어주었고, 또, 지원한 파트를 가지고 있지 않는 지원자는 이 동아리엔 없기 때문에, 식별 관계로 만들어주었다!
 
또한, 지원자 한명은 하나의 파트에만 지원할 수 있고, 하나의 파트에는 여러명이 지원할 수 있는 비즈니스 로직을 고려하여, 1:N 관계로 지원자와 파트 테이블의 연관 관계를 만들어주었다.

> ## 개발 방향 및 특이사항

```java
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.0.2'
	id 'io.spring.dependency-management' version '1.1.0'
}
```

프레임워크 버전은 다음과 같이 세팅하였다. JAVA 17을 사용하여 프로젝트를 진행하였다.

```java
@Getter
@Entity
public class Part {

    @Id
    @GeneratedValue
    @Column(name="part_id")
    private int id;

    @OneToMany(mappedBy = "part")
    private List<Applicant> applicants=new ArrayList<Applicant>();
    private String partName;
}
```
먼저, Part 도메인을 개발하였다. PK 값으로 part_id을 지정해주었고, 혹시나 향후에 쓰일 수 있으니, Part 도메인에서도 applicants를 조회할 수 있게 양방향 관계로 코드를 작성해주었다.


```java
@Entity
@Table(name="applicants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="part_id")
    private Part part;
    ~~~~    
    
```
그리고, 지원자에 해당하는 Applicant 도메인을 작성해주었다. 마찬가지로 id값을 Pk로 두었으며, part_id를 JoinColumn으로 part에서 fk로 가져와주었다.

### 도메인 모델 패턴

```java
@Enumerated(EnumType.STRING)
    private AbsenseStatus absenseStatus;


    public static Applicant createApplicant(Part part, String name, String address,String subway, String email,String school, String major, String question1, String question2, String question3,AbsenseStatus absenseStatus){
        Applicant applicant=new Applicant();
        applicant.setPart(part);
        applicant.setAddress(address);
        applicant.setEmail(email);
        applicant.setName(name);
        applicant.setSubway(subway);
        applicant.setSchool(school);
        applicant.setMajor(major);

        applicant.setQuestion1(question1);
        applicant.setQuestion2(question2);
        applicant.setQuestion3(question3);
        applicant.setAbsenseStatus(absenseStatus);
        return applicant;
    }
```
그리고, Applicant를 만들어주는 createApplicant를 생성해주었고, 도메인 모델 패턴으로 코드를 작성하였다. 이유는, applicant가 가지고 있는 속성이 너무나도 많기에.. 일일이 생성할때마다 set해주는것은 너무나도 불필요한 코드 낭비가 많을 것 같아, 도메인 모델 패턴으로 생성자를 대체하였다.


```
@NoArgsConstructor(access = AccessLevel.PROTECTED)
```
를 통해서, JPA에서 기존의 Applicant 생성자를 사용하지 못하도록  막았다. JPA는 Protected Level에 AccessLevel에 접근하지 못한다.

### 레포지토리 

```
public List<Applicant> findAll(){
        return em.createQuery("select a from Applicant a",Applicant.class).getResultList();
    }

    public List<Applicant> findByName(String name){
        return em.createQuery("select a from Applicant a where a.name=:name",Applicant.class).setParameter("name",name).getResultList();
    }

    public List<Applicant> findByPart(String part_id){
        return em.createQuery("select a from Applicant a where a.part.id=:part_id",Applicant.class).setParameter("part_id",part_id).getResultList();
    }
```
마찬가지로, Applicant와 Part의 Repository를 만들어주었는데, ApplicantRepository에 재미난 기능을 많이 넣었다. 지원자 전체를 리턴해주는 findAll, 그리고 이름을 통해 지원자를 찾을 수 있는 findByName, 그리고 이 프로젝트의 핵심 개발요소였던 part_id만을 바탕으로 그 파트에 지원한 지원자들을 리턴하는 findByPart를 jpql을 통해 구현해주었다.

### 데이터베이스 초기 세팅 설정

```sql
insert into part(part_id,part_name) values(1,'서버');
insert into part(part_id,part_name) values(2,'웹');
insert into part(part_id,part_name) values(3,'안드로이드');
insert into part(part_id,part_name) values(4,'ios');
insert into part(part_id,part_name) values(5,'디자인');
insert into part(part_id,part_name) values(6,'기획');
```
서비스를 개발하기전에, 생각해보니 SOPT에는 6가지의  파트가 이미 생성되있어야한다는 것을 깨달았다! 그래서, 잊기전에 import.sql을 생성하여 초기 세팅에 6개의 파트가 이미 생성되있는 구조를 만들어주었다.

### 서비스 개발

```java
@Transactional
    public int apply(Applicant applicant){
        applicantRepository.save(applicant);
        return applicant.getId();
    }

    public List<Applicant> findApplicants(){
        return applicantRepository.findAll();
    }

    public List<Applicant> findByPart(String part_id){
        return applicantRepository.findByPart(part_id);
    }
```

그리고, 이 Repository와, 도메인 모델 패턴을 사용했던 만큼 서비스 개발은 굉장히 쉽게 만들 수 있었다. 기존의 만들었던 메서드들을 사용하는 형식으로, Service 클래스를 깔끔하게 작성해주었다. 

> ## 대망의 컨트롤러

### 지원하기
```java
@GetMapping(value = "/apply")
    public String createForm(Model model){
    model.addAttribute("applyForm",new ApplicantForm());
    return "apply/applyForm";
    }
    @PostMapping(value = "/apply")
    public String apply(ApplicantForm applicantForm){
    String partName=applicantForm.getPartName();
    Part part=partService.findPart(partName);
    Applicant applicant=Applicant.createApplicant(part,applicantForm.getName(),applicantForm.getAddress(),applicantForm.getSubway(),applicantForm.getEmail(),applicantForm.getSchool(),applicantForm.getMajor(),applicantForm.getQuestion1(),applicantForm.getQuestion2(),applicantForm.getQuestion3(),applicantForm.getAbsenseStatus());

    applicantService.apply(applicant);
    return "redirect:/";
    }
```

그리고, 마지막으로 컨트롤러를 작성해주었다. 먼저 작성한 부분은 지원자가 지원을 하는 apply부분인데, 여기선 템플릿을 사용했기에, Model을 사용하여 데이터를 넘겨주었다. Get 메서드가 매핑되었을때, applyForm.html을 넘겨주었고, 그 넘겨준 페이지에서 폼 데이터 형식으로 post 요청이 되었을때, 지원자의 정보를 apply 메서드를 통해 persist해주었다.

추가로, 여기서 createApplicant를 이용해서, 도메인 모델 패턴으로 만들어준 생성자 메서드를 사용할 수 있었다! 

### 파트별 지원자 조회하기
```java
@GetMapping(value = "/check/{partName}")
    public String findPartApplicant(@PathVariable String partName, Model model){
    Part part=partService.findPart(partName);
    int partId=part.getId();
    List<Applicant> applicantList= applicantService.findByPart(Integer.toString(partId));
    model.addAttribute("applicants",applicantList);
    model.addAttribute("partName",partName);
    return "/applicantList";

    }
```
partName을 url의 PathVariable로 받아준 후, 그 파트의 name을 바탕으로 어떤 파트인지 find 해주었다. 그리고 그 파트의 partId를 get 한뒤, partId를 이용해 지원한 지원자들에 대한 정보를 ApplicantList로 받아올 수 있었다.

그 결과,
![](https://velog.velcdn.com/images/hayounsong/post/4af8419d-60ca-4f00-bf3f-46dd852cafd9/image.png)

다음과 같이 데이터를 사용자에게 제공할 수 있었다. (template 엔진이기에 , SSR 방식으로!)



> ### 프로젝트를 마치면서..

간단한 프로젝트였고, 사실, 간단한 리크루팅 과정을 클론코딩하는데에 생각보다 많은 리소스가 필요해서 놀랐다. 그러나, 이 리크루팅 과정을 클론코딩하면서 생각보다 많은 것을 배울 수 있었고, 기존에도 백엔드는 어느정도 공부하고 프로젝트도 해봤지만, 다소 난잡했던 나의 스프링 개념을 어느정도 정리할 수 있어서 도움이 되었던 것 같다. 

시간이 된다면, 저 프로젝트를 REST API 형식으로 컨트롤러를 리팩토링하고, react 프로젝트를 하나 만들어서 통신하고 싶다.
