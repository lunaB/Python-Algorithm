#define _CRT_SECURE_NO_WARNINGS
#define MAX2(a,b) (a)>(b)?(a):(b)

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/*
	제작자 : 나영채
	제작일 : 16-10-29
*/

double weight = 2.0;
double bias = 1.0;

double input;
double output;
double delta = 0.1; // 변화량 델타

double getActivation(const double x) {
	return x;
	//ReLU 양
	//return MAX2(2, 0);
}
double getActGrad(const double x) {
	return 1.0;
	//ReLU양
	//return MAX2(x,0); 
}
void propBacward(const double target) {
	//const double delta = 0.1;
	const double grad = (output - target) * getActGrad(output);
	weight += -delta * grad * input; //d(wx+b)/dw = x
	bias += -delta * grad * 1.0; //d(wx+b)/db = 1
}
double feedForward(const double input_t) {
	input = input_t;
	const double sigma = weight * input + bias;
	output = getActivation(sigma);
	return output;
}

struct training
{
	double input_training;
	double output_tranining;
};

int main() {
	system("title 나영채 16-10-29 ra20617.dothome.co.kr/dream");
	struct training t[5];
	FILE *f;
	f = fopen("trainingFile.txt", "r");
	const int line = 10;
	for (int i = 0; i < 5; i++) {
		fscanf(f, "%lf %lf\n",&t[i].input_training,&t[i].output_tranining);
	}
	fclose(f);

	int learnNum = 0;
	printf("delta : ");
	scanf("%lf", &delta);
	printf("Number of learning : ");
	scanf("%d", &learnNum);

	srand((unsigned)time(NULL));
	puts("training");
	for (int i = 0; i < learnNum; i++) {
		int r = rand()%5;
		feedForward(t[r].input_training);
		propBacward(t[r].output_tranining);
		printf("no=%d r=%d w=%f a=%f out=%f\n", i, r, weight, bias, output);
	}

	while (1) {
		double tmp;
		puts("test nur");
		printf("::");
		scanf_s("%lf", &tmp);
		printf("result=%f\n", feedForward(tmp));
	}
}
