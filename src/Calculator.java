import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator extends JFrame {

    private JTextField inputSpace;
    private String num ="";//숫자를 담을 변수
    private ArrayList<String> equation = new ArrayList<String>();//계산기능을 구현하기 위해 ArrayList에 숫자와 연산기호를 하나씩 구분해 담아줄겁니다

    public Calculator(){
        setLayout(null);

        inputSpace = new JTextField();
        inputSpace.setEditable(false);//편집가능여부 불가능
        inputSpace.setBackground(Color.WHITE);//배경색
        inputSpace.setHorizontalAlignment(JTextField.RIGHT);//정렬위치
        inputSpace.setFont(new Font("Arial",Font.BOLD,50));//글씨체
        inputSpace.setBounds(8,10,270,70);

        //두번째
        JPanel buttonPanel = new JPanel();//버튼들을 담을 패널을 만들어 준다
        buttonPanel.setLayout(new GridLayout(4,4,10,10));//격자형태로 배치해주는 GridLayout을 사용(가로,세로,좌우간격,상하간격)
        buttonPanel.setBounds(8,90,270,235);//위치와 크기를 설정

        String button_names[] = {"C","÷","×","=","7","8","9","+","4","5","6","-","1","2","3","0"};//ㄷ한자 누르면 기호 나옴
        JButton buttons[] = new JButton[button_names.length];
        for(int i=0; i<button_names.length; i++){
            buttons[i]= new JButton(button_names[i]);
            buttons[i].setFont(new Font("Arial",Font.BOLD,20));

            if(button_names[i]=="C")buttons[i].setBackground(Color.RED);
            else if ((i>=4&&i<=6)||(i>=8&&i<=10)||(i>=12&&i<=14)) buttons[i].setBackground(Color.BLACK);
            else buttons[i].setBackground(Color.GRAY);
            buttons[i].setForeground(Color.WHITE);//글자색
            buttons[i].setBorderPainted(false);//테두리 없애주기
            buttons[i].addActionListener(new PadActionListener());//네번째 액션리스너를 버튼에 추가해줍니다
            buttonPanel.add(buttons[i]);//버튼들을 버튼패널에 추가
        }


        add(inputSpace);
        add(buttonPanel);

        //첫번째
        setTitle("계산기");//타이틀 바 내용
        setVisible(true);//창을 화면에 나타낼 것인지 설정
        setSize(300,370);
        setLocationRelativeTo(null);//화면의 가운데에 띄우며
        setResizable(false);//사이즈 조절을 불가능하게
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//CloseOperation도 설정해주어야 창닫을때 실행중인 프로그램도 같이 종료
    }


    //세번째
    class PadActionListener implements ActionListener{//ActionListener를 상속시켜주고
        public void actionPerformed(ActionEvent e){//actionPerformed(ActionEvent e)메소드로 이벤트 처리해주면 됨
            String operation = e.getActionCommand();//e.getActionCommand()로 어떤 버튼이 눌렸는지 받아옵니다
            if(operation.equals("C")){
                inputSpace.setText("");
            }
            else if (operation.equals("=")) {//여섯번째
                String result = Double.toString(calculate(inputSpace.getText()));
                inputSpace.setText(""+result);
                num="";
            }
            else{
               inputSpace.setText(inputSpace.getText()+e.getActionCommand());//나머지 버튼의 경우 눌렀을 시 계산식에 추가 되도록!!

            }
        }
    }

    //다섯번째
    private void fullTextParsing(String inputText){
        equation.clear();//배열 변수

        for(int i=0; i<inputText.length(); i++){
            char ch = inputText.charAt(i);

            if(ch == '-'| ch == '+' | ch == '×' | ch == '÷'){
                equation.add(num);//앞은 숫자이므로 ArrayList에 추가
                num="";//num을 초기화 해주고
                equation.add(ch+"");//연산기호를 ArrayList에 추가
            }
            else{
                num = num + ch;//예를 들어 67+9경우
            }
        }
        equation.add(num);//반복문이 끝나고 최종으로 있는 num도 ArrayList에 추가해줍니다
    }

    public double calculate(String inputText){// 계산 기능
        fullTextParsing(inputText);

        double prev = 0;
        double current = 0;
        String mode ="";

        for(String s : equation){
            if(s.equals("+")){
                mode="add";
            }
            else if(s.equals("-")){
                mode ="sub";
            }
            else if(s.equals("×")){
                mode ="mul";
            }
            else if(s.equals("÷")){
                mode ="div";
            }
            else{
                current = Double.parseDouble(s);//나머지(숫자)의 경우 문자열을 Double로 형변환을 해줘야 합니다
                if(mode=="add"){
                    prev += current;
                }
                else if(mode=="sub"){
                    prev -= current;
                }
                else if(mode=="mul"){
                    prev *= current;
                }
                else if(mode=="div"){
                    prev /= current;
                }
                else{
                    prev = current;
                }
            }
        }
        return prev;//계산값 반환
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
