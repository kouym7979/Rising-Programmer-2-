## 안드로이드 Layout 정리

##### LinearLayout

단일 방향(가로, 세로)으로 모든 하위 요소를 정렬하는 뷰 그룹이다.

- layout_orientation속성:

  기본은 horizontal로 지정되어있으며 android:orientation="vertical" or android:orientation="horizontal"로 수직과 수평배치를 지정할 수 있습니다.

- layout_gravity와 gravity차이:

  LinearLayout에서 내부 값들을 정렬할 때 layout_gravity는 뷰를 정렬하는 데 사용되고 gravity는 뷰 안에 들어있는 내용물을 정렬할 때 사용됩니다.

- layout_weight 속성:

  자식 뷰에 가중치를 지정해서 그 비율만큼의 자식 뷰의 크기를 지정하는 속성입니다.

- ViewGroup 안에 ViewGroup 포함하기:

  ViewGroup안에 ViewGroup이 포함되는 것이 가능합니다. ViewGroup을 마치 View 취급을 하는 것인데 쉽게 LinearLayout안에 LinearLayout을 포함할 수 있습니다.

##### LinearLayout의 단점: 

######  	수직, 수평 배치로 간단하게 배치가 가능하지만 성능면에서 많이 떨어집니다.

##### LinearLayout의 장점: 

- 다른 레이아웃에 비해 사용하기 편하며 간단하게 비율로 정리되는 장점을 가지고 있습니다.

---

##### Constraint Layout

Constraint Layout은 제약조건인 연결선을 통해 그 안에 추가된 뷰들의 위치를 결정합니다.

연결선은 뷰의 상, 하, 좌, 와에 있는 연결점을 다른 레이아웃이나 위젯의 상, 하, 좌, 우와 연결하여 만들 수 있다.

하지만 뷰의 위치를 결정할 수 있을 만큼의 연결선이 없으면 해당 뷰는 위치할 수 없게 된다.

##### Constraint Layout의 장점

- Linear Layout이나 Relative Layout에 비해서 뷰 비율 조절도 간단히 가능합니다.( depth가 깊어지는 것을 방지)

- 뷰 게층을 간단히 할 수 있어 유지보수도 좋고 성능도 좋습니다.

----

##### Relative Layout

Relative Layout은 뷰를 담고 있는 부모 레이아웃이나 그 안에 들어있는 다른 뷰들과의 상대적 위치를 이용해 화면을 배치하는 레이아웃이다.

##### Relative Layout의 장점

- Relative Layout은 중첩된 뷰 그룹을 없애고 레이아웃 계층 구조를 평면으로 유지하여 성능을 개선 사용자 인터페이스 설계에 매우 유용합니다.
- 여러개의 중첩된 LinearLayout그룹을 사용한 다면 단일 RelativeLayout으로 대체할 수 있습니다.



---

##### TableLayout

TableLayout은 표와 같은 형태를  만들기위해 RelativeLayout을 사용하게 될때, 모든 자식 뷰에 상대적 위치 지정을 위한 속성을 일일이 지정해야해서 번거로운 부분과 LinearLayout을 사용하면 자식 뷰들의 가중치를 하나하나 지정해줘야하는 불편한 점이 있습니다.



다음은 사용방법입니다.

```
<TableLayout
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <TableRow>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:textSize="24sp"
                android:background="#F44336"
                android:textColor="#FFFFFF"
                android:text="A" />
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:textSize="24sp"
                android:background="#9C27B0"
                android:textColor="#FFFFFF"
                android:text="B" />
        </TableRow>
        <TableRow>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:textSize="24sp"
                android:background="#3F51B5"
                android:textColor="#FFFFFF"
                android:text="1" />
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:textSize="24sp"
                android:background="#00BCD4"
                android:textColor="#000000"
                android:text="2" />
        </TableRow>
    </TableLayout>
```

___

##### Grid Layout

GridLayout은 2차원 격자무늬 형태의 레이아웃으로 행과 열의 집합형태로 구성된 레이아웃입니다.

TableLayout의 단점을 보완한 레이아웃으로 LinearLayout 또는 FrameLayout과 같은 다른 레이아웃의 장점을 포함하는 레이아웃입니다.

GridLayout 속성:orientation, rowCount, columnCount, alinmentMode

GridLayout은 rowCount, columnCount로 각 행의 개수와 열의 개수를 정해서 정해진 뷰의 개수가 넘어가면 자동적으로 행 또는 열이 추가가됩니다.

alignmentMode 속성은 자식 뷰들을 정렬할 때 기준이 되는 뷰의 여백을 포함하여 나머지 뷰들을 정렬할지를 결정하는 속성입니다. 이때 기준이 되는 뷰란 가장 큰 너비 혹은 높이를 가지는 뷰를 의미합니다.

##### Grid Layout의 장점

- 다른 레이아웃과 중첩으로 사용할 필요가 없어 메모리 사용량을 줄일 수 있다는 장점이 있습니다.

___



##### FrameLayout

여러 개의 뷰를 중첩으로 배치하고 그중 하나를 레이아웃의 전면에 표시할 때 사용하는 레이아웃입니다.

전면에 나오는 뷰만 표시되는 것은 아닙니다. 위에서는 FrameLayout에 중첩되는 뷰들이 모두 통일한 크기일 때 얘기입니다. 만약 위 그림처럼 가장 마지막에 들어간 뷰의 크기가 먼저 들어간 뷰보다 작으면 먼저 들어간 뷰의 일부도 나타날 수 있습니다.

아래는 기본적인 사용예시입니다.

```
  <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="24sp"
            android:background="#4CAF50"
            android:id="@+id/text1"
            android:text="TEXT1" />

    </FrameLayout>
```

