## 안드로이드 Intent와 관련된 Manifest -2

##### android:taskAffinity

액티비티는 상하 관계가 있는 작업입니다. 상하 관계가 동일한 액티비티는 개념상 동일한 작업(사용자의 관점에서 동일한 "애플리케이션")에 속합니다. 작업의 상하 관계는 루트 액티비티의 상하 관계가 결정합니다.

상하 관계는 액티비티가 상위로 재지정한 작업(allowTaskReparenting)및 FLAG_ACTIVITY_NEW_TASK플래그를 사용하여 시작할 때 액티비티를 수용하는 작업, 이렇게 두가지를 결정합니다.

기본적으로 애플리케이션 내 모든 액티비티의 상하 관계는 동일합니다. 액티비티를 서로 다르게 그룹화하도록 이 특성을 설정할 수 있으며, 서로 다른 애플리케이션에 정의된 액티비티를 동일한 작업 안에 둘 수도 있습니다. 액티비티가 작업에 대한 상하 관계가 없도록 지정하려면 빈 문자열로 설정하면 됩니다.



##### taskAffinity란

taskAffinity를 이해하기 위해서는 Activity task란 개념을 먼저 이해를 해야합니다.

Task란 사용자의 입장에서 하나의 어플리케이션 처럼 보이는 activity들의 집합을 말합니다.

Task는 activity를 stack형태로 관리합니다.

​	-Root activity: task를 시작한 Activity

​	-Top activity: task stack의 가장 상위 Activity

![task](https://user-images.githubusercontent.com/52284829/103435898-e8b5e580-4c58-11eb-9ef8-d01ccc1a2af7.png)

즉, taskAffinity는 Activity실행 시 신규 task를 만들 경우 어느 task에 속할지 지정

(AndroidManifest.xml에) "android:taskAffinity"로 지정하며 다음과 같은 플래그 속성을 사용한다.

​	- FLAG_ACTIVITY_SINGLE_TOP

​	- FLAG_ACTIVITY_NEW_TASK

​	- FLAG_ACTIVITY_MULTIPLE_TASK

모든 activity는 affinity를 가지고 있으며 값을 명시하지 않을 시 디폴트로 앱의 패키지명이 된다.



##### taskAffinity명시 

taskAffinity는 패키지 명의 규칙과 동일하며 ex) android: taskAffinity="com.example.A"와 같이 작성한다.

___

##### android:allowTaskReparenting

액티비티를 시작한 작업에서 해당 작업이 다음에 앞으로 나올 때 액티비티를 선호하는 그다음 작업으로 이동할 수 있는지를 나타내는데, 이 값이 "True"인 경우 이동가능하고 "False"인 경우 시작한 작업에 그대로 머물러 있어야 합니다.

이 특성을 설정하지 않는 경우 <application>요소에 해당 특성이 설정한 값이 액티비티에 기본값  "false"로 적용됩니다.

taskAffinity특성이 액티비티의 상하 관계를 정의합니다. 작업의 상하 관계는 루트 액티비티의 상하 관계에 의해 결정됩니다. 따라서 정의상 루트 액티비티는 항상 상하 관계가 동일한 작업에 있습니다.

"singleTask" 또는 "singleInstance" 시작 모드 액티비티는 작업 루트에만 있을 수 있으므로 상위 액티비티로의 재지정은 "standard" 및 "singleTop" 모드로 제한됩니다.



___

##### android:alwaysRetainTaskState

시스템이 액티비티의 작업 상태를 항상 유지관리하는지 여부를 나타내는데, 이 값이 "true"인 경우 항상 유지관리하며 "false"인 경우 시스템이 특정 상황에서 작업을 초기 상태로 다시 설정할 수 있습니다. 기본값은 "false"입니다. 이 특성은 작업의 루트 액티비티에만 적용되며 다른 모든 액티비티에 대해서는 무시됩니다.

->즉, false를 하게 되면 system의 정책상 일정 시간이 지나면 root activity를 제외한 task stack을 초기화 시킵니다.

일반적으로 시스템은 사용자가 홈 화면에서  작업을 다시 선택하는 특정 상황에서 작업을 지웁니다(루트 액티비티 위 스택에서 모든 액티비티를 제거함). 일반적으로 지우기는 사용자가 30분과 같이 특정 시간 동안 작업을 방문하지 않는 경우 수행됩니다.

그러나 이 특성이 "true"인경우 사용자는 도착 방법과 상관없이 항상 마지막 상태의 작업으로 돌아갑니다. 이는 예컨대 사용자가 손실을 원하지 않는 상태(ex: 복수의 열린 탭)인 많은 웹브라우저와 같은 애플리케이션에서 유용합니다.

