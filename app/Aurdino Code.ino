#include <math.h>

#define In1 5  //Left Wheel
#define In2 2  //Left Wheel

#define In3 3  // Right Wheel
#define In4 4

#define win1 11  // Right Wheel
#define win2 12

const int trigPin = 7;
const int echoPin = 8;
// defines variables
long duration;
int distance;
const int trigPin1 = 10;
const int echoPin1 = 9;
long duration1;
int distance1;

const int trigPin2 = A1;
const int echoPin2 = A2;
long duration2;
int distance2;

//const int trigPin2 = 8;
//const int echoPin2 = 9;
int data;
int command;
int Speed = 255;
int t = A0;
int ir = 13;


#define x A3  // x_out pin of Accelerometer
#define y A4  // y_out pin of Accelerometer
#define z A5
int xsample = 0;
int ysample = 0;
int zsample = 0;
long start;
#define samples 50


void setup() {
  pinMode(In1, OUTPUT);
  pinMode(In2, OUTPUT);
  pinMode(In3, OUTPUT);
  pinMode(In4, OUTPUT);

  pinMode(win1, OUTPUT);
  pinMode(win2, OUTPUT);
  pinMode(t, INPUT);
  pinMode(ir, INPUT);


  
  pinMode(trigPin, OUTPUT);  // Sets the trigPin as an Output
  pinMode(echoPin, INPUT);
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
  digitalWrite(In1, LOW);
  digitalWrite(In2, LOW);
  digitalWrite(In3, LOW);
  digitalWrite(In4, LOW);
  digitalWrite(win1, LOW);
  digitalWrite(win2, LOW);
  Serial.begin(9600);

  for (int i = 0; i < samples; i++)  // taking samples for calibration
  {
    xsample += analogRead(x);
    ysample += analogRead(y);
    zsample += analogRead(z);
  }

  xsample /= samples;  // taking avg for x
  ysample /= samples;  // taking avg for y
  zsample /= samples;  // taking avg for z

  delay(1000);  //
}

void winup() {

  digitalWrite(win1, HIGH);
  digitalWrite(win2, LOW);
  //Serial.println("win up");
}

void windown() {
  digitalWrite(win1, LOW);
  digitalWrite(win2, HIGH);
  //Serial.println("win down");
}

void winstop() {
  digitalWrite(win1, LOW);
  digitalWrite(win2, LOW);
  //Serial.println("win Stop");
}

void right() {
  digitalWrite(In1, LOW);
  digitalWrite(In2, HIGH);
  digitalWrite(In3, LOW);
  digitalWrite(In4, LOW);
  //Serial.println("right");
}
void left() {
  digitalWrite(In1, LOW);
  digitalWrite(In2, LOW);
  digitalWrite(In3, LOW);
  digitalWrite(In4, HIGH);
  //Serial.println("right");
}
void forward() {
  //Serial.println("forward");
  digitalWrite(In1, LOW);
  digitalWrite(In2, HIGH);
  digitalWrite(In3, LOW);
  digitalWrite(In4, HIGH);
 
}
void backward() {
  //Serial.println("backward");
  digitalWrite(In1, HIGH);
  digitalWrite(In2, LOW);
  digitalWrite(In3, HIGH);
  digitalWrite(In4, LOW);
  //obstacle();
}
void stop_motor() {
  //Serial.println("stop");
  digitalWrite(In1, LOW);
  digitalWrite(In2, LOW);
  digitalWrite(In3, LOW);
  digitalWrite(In4, LOW);
}
void obstacle1() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);
  // Calculating the distance
  distance = duration * 0.034 / 2;
  
  if (distance < 30) {
    stop_motor();
    //delay(5000);
    Serial.println("vehicle stopped due to obstacle detected Front Side");
  }
  //delay(1000);
  digitalWrite(trigPin1, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration1 = pulseIn(echoPin1, HIGH);
  // Calculating the distance
  distance1 = duration1 * 0.034 / 2;
  
  if (distance1 < 30) {
    stop_motor();
    //delay(5000);
    Serial.println("vehicle stopped due to obstacle detected Left Side");
  }
  //
  digitalWrite(trigPin2, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin2, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin2, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration2 = pulseIn(echoPin2, HIGH);
  // Calculating the distance
  distance2 = duration2 * 0.034 / 2;
  
  if (distance2 < 30) {
    stop_motor();
    //delay(5000);
    Serial.println("vehicle stopped due to obstacle detected Right Side");
  }
}
void acc() {
  int sensorStatus = digitalRead(t);
  
  if (sensorStatus == 0) {
    Serial.println("vehicle stop due to accident detected from impact sensor");
    stop_motor();
  }
}

void loop() {
  String ins = "";
  obstacle1();
  acc();


  if (Serial.available() > 0) {
    char data = Serial.read();
    //Serial.println(data);
    ins = String(data);
  }
  
  int i = digitalRead(ir);
  

  if (ins == "u") {
    for (int i = 0; i < 40; i++) {

      int ii = digitalRead(ir);

      
      while (digitalRead(ir) == 0) {

        winstop();
        Serial.println("Window Stop");
      }
      if (ii == 1) {
        Serial.println("Window Up");
        winup();
      }

      delay(100);
    }
    Serial.println("Window Stop");
    winstop();
  }

  if (ins == "d") {
    for (int i = 0; i < 40; i++) {

      int ii = digitalRead(ir);

      
      while (digitalRead(ir) == 0) {

        winstop();
        Serial.println("Window Stop");
      }
      if (ii == 1) {
        Serial.println("Window Down");
        windown();
      }

      delay(100);
    }
    Serial.println("Window Stop");
    winstop();
  }


  if (ins == "f") {

    forward();
  }
  if (ins == "b") {
    backward();
    
  }
  if (ins == "r") {
    right();
    
  }
  if (ins == "l") {
    left();
    
  }
  if (ins == "s") {
    stop_motor();
  }

  int value1 = analogRead(x);  // reading x out
  int value2 = analogRead(y);  //reading y out
  int value3 = analogRead(z);  //reading y out

  int xValue = xsample - value1;  // finding change in x
  int yValue = ysample - value2;  // finding change in y
  int zValue = zsample - value3;  // finding change in y

  if (xValue > 50 || xValue < -70) {
    Serial.print("vehicle stop due to accident detected from ADXL sensor");
    stop_motor();
  }
  if (zValue > 30) {
    Serial.print("vehicle stop due to accident detected from ADXL sensor");
    stop_motor();
  }
  

  ins = "";
}
