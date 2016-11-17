#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MAX2(a,b) (a)>(b)?(a):(b)

/*
	제작자 : 나영채
	제작일 : 16-10-29
*/

double weight = 2.0;
double bias = 1.0;

double input;
double output;

double getActivation(const double x) {
	return x;
	//ReLU
	//return MAX2(2, 0);
}
double getActGrad(const double x) {
	return 1.0;
	//ReLU
	//return MAX2(x,0); 
}
void propBacward(const double target) {
	const double alpha = 0.1;
	const double grad = (output - target) * getActGrad(output);
	weight += -alpha * grad * input; // last input came from d(wx+b)/dw = x
	bias += -alpha * grad * 1.0; //last 0.1 came from d(wx+b)/db = 1
}
double feedForward(const double input_t) {
	input = input_t;
	const double sigma = weight * input + bias;
	output = getActivation(sigma);
	return output;
}
int main() {
	puts("training");
	double in_arr[3] = { 1.0,2.0,3.0 };
	double out_arr[3] = { 100.0,200.0,300.0 };
	srand((unsigned)time(NULL));
	for (int i = 0; i < 10000; i++) {
		int k = rand()%3;
		feedForward(in_arr[k]);
		propBacward(out_arr[k]);
		printf("no=%d w=%f a=%f out=%f\n", i, weight, bias, output);
	}
	double tmp;
	while (1) {
		scanf_s("%lf", &tmp);
		printf("result=%f\n", feedForward(tmp));
	}
}