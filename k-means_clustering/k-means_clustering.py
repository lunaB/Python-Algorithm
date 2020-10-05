
# coding: utf-8

# In[1]:

# k - 평균 알고리즘 ( 군집화 )
# 나영채


# In[11]:

import numpy as np
class KMeanClustering():
    def __init__(self, k, complete=0.001):
        self.k = k
        self.complete = complete

    def run(self, data):
        kp = np.random.rand(2,self.k)
        while True:
            distance = np.array([np.sqrt((kp[0,i]-data[0])**2+(kp[1,i]-data[1])**2) for i in range(self.k)])
            near_sum = np.zeros((2, self.k)) # 가장 가까운 수 더함
            near_num = np.zeros(self.k)      # 가장 가까운 점 갯수
            for i in range(data_len):
                n = np.argmin(distance.T[i]) # 거리가 좁은 배열번호 0~2
                near_num[n] += 1
                near_sum[0,n] += data[0,i]
                near_sum[1,n] += data[1,i]
            if np.sum((near_sum/near_num-kp)**2)<self.complete:
                break
            kp = near_sum/near_num
        return kp
    def qurey(x,y):
        return np.argmin([(kp.T[i,0]-x)**2 + (kp.T[i,1]-y)**2 for i in range(k)])

