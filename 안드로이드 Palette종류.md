## 안드로이드 Palette종류

##### TextView

TextView는 안드로이드 UI를 구성함에 있어 화면에 텍스트를 표시하는 기능을 담당하며, 안드로이드에서 제공하는 위젯 중 가장 많이 사용되는 위젯입니다. 텍스트 출력 기능을 가진 기본 위젯이므로, 텍스트와 연관된 기능을 포함하는 Button 또는 EditText의 부모 클래스이기도 합니다. 

- ##### 관련 종류

  Plain Text, Password, E-mail, Phone, Time, Data, Number(Signed, Decimal),AutoCompleteTextView 다음과 같은 다양한 속성들로 비밀번호, 이메일, 전화번호 등 다양한 방식으로 텍스트를 입력하는 키보드를 다양화할 수 있습니다.



##### Button

Button은사용자가 화면을 터치했을 때 발생하는 클릭 이벤트를 처리하는 기능을 가진, 텍스트 또는 아이콘으로 구성된 View 위젯입니다. TextView와 마찬가지로 안드로이드 UI를 구성할 때 가장 많이 사용되는 위젯 중 하나입니다.

Button은 기본적으로 사용자의 터치 이벤트를 처리할 수 있기 때문에 TextView와는 다른 목적으로 사용되지만, 거의 모든 기능을 TextView로부터 상속하고 있습니다.

즉, Button 클래스의 부모클래스는 TextView이며, TextView에서 정의된 대부분의 속성이나 기능들을 Button에서 그대로 사용할 수 있습니다.

- ##### 관련 종류

  - ImageButton: 사용자가 누르거나 클릭할 수 있는 이미지가 있는 버튼을 표시
  - CheckBox: 선택 또는 선택 해제할 수 있는 유형의 상태 버튼
  - RadioGroup: 라디오 버튼을 사용하면 세트에서 하나의 옵션을 선택, 사용자가 사용 가능한 모든 옵션을 나란히 볼 필요가 있다고 생각되면 상호 배탁적인 옵션 세트에 사용
  - ToggleButton: 토글 버튼을 사용하면 두 상태 사이에서 설저을 변경(ex: off, on)
  - Switch: 스위치는 두 가지 옵션 중에서 선택할 수 있는 2 상태 토글 스위치 위젯, 드래그하여 선택한 옵션을 선택하거나 간단히 탭하여 마치 확인란인 것처럼 토글
  - FloatingActionButton: UI 위에 떠있는 원 아이콘으로 구별되며 모핑, 시작 및 전송 앵커 포인트와 관련된 특수 동작이 있다.



##### Widgets

앱 위젯은 다른 애플리케이션에 삽입되어 주기적인 업데이트를 받을 수 있는 소형 애플리케이션뷰입니다. 이러한 뷰는 사용자 인터페이스에서 위젯이라고하며 앱 위젯 공급자를 사용하여 게시할 수 있습니다. 다른 앱 위젯을 포함할 수 있는 애플리케이션 구성요소를 앱 위젯 호스트라고 합니다.

즉, 안드로이드 어플리케이션 화면상을 구성하는 visual UI elements를 통칭하는 패키지 이름입니다.

- ##### 관련 종류

  - ImageView: 이미지를 표시 자원, Bitmap 또는 Drawble자원, ImageView는 일반적으로 이미지에 색조를 적용하고 이미지 스케일링을 처리하는 데 사용
  - WebView: 각 OS별 내장 된 웹브라우저를 뷰형태로 앱에서 표현 할 수 있는 방법
  - CalendarView:  탭, 클릭 이벤트로 날짜를 선택할 수 있으며 원하는 날짜로 달력을 스크롤 하고 찾는 기능
  - ProgressBar: 가로 모양의 작업 진행률을 나타내는 사용자 인터페이스 요소
  - SeekBar: 드래그 가능한 썸을 추가하는 ProgressBar의 확장 위젯. 사용자는 엄지 손가락을 터치하고 왼쪽 또는 오른쪽으로 끌어 현재 진행률 수준을 설정하거나 화살표 키를 사용
  - RatigBar: SeekBar 및 ProgressBar의 확장으로 별표로 등급을 표시
  - SearchView: 사용자가 검색어 입력 후 search provider에게 검색을 요청할 수 있는 UI를 제공, 검색어 추천 리스트나 검색 결과 리스트를 보여주고 사용자는 해당 리스트에서 원하는 item을 선택할 수 있다.
  - ScrollView: 하나의 배치된 뷰 계층 구조를 스크롤 할 수 있는 뷰 그룹
  - HorizontalScrollView: ScrollView가 위아래라면, 좌우로 스크롤이 가능한 뷰 그룹
  - NestedScrollView: ScrollView과 동일하지만 중첩 스크롤 부모와 자식 역할을 모두 지원
  - ViewPager: 사용자가 데이터 페이지를 좌우로 넘길 수 있는 레이아웃 관리자
  - CardView: 둥근 모서리 배경과 그림자가 있는 FrameLayout
  - NavigaionView: 응용 프로그램의 표준 탐색 메뉴를 나타냄
  - BottomNavigationView: 응용 프로그램의 표준 하단 탐색 모음을 나타냅니다. 



