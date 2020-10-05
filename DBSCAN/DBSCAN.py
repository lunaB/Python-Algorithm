
# coding: utf-8

# In[1]:


# DBSCAN (Density-based spatial clustering of application with noise)(군집화)
# 나영채


# In[2]:


import numpy as np
import matplotlib.pyplot as plt


# In[3]:


# 데이터 세팅


# In[4]:


#x|y|표준편차|갯수
def dset(x,y,s,n):
    data = np.array([np.random.normal(x, s, n),np.random.normal(y, s, n)])
#     data[0] = data[0]/np.max(data[0])
#     data[1] = data[1]/np.max(data[1])
    return data


# In[5]:


a = dset(4, 4, 0.4, 70)
b = dset(5, 5, 0.4, 70)
c = dset(8, 5, 0.4, 70)
d = dset(8, 8, 1, 10)
data = np.array([np.concatenate((a[0],b[0],c[0],d[0])), np.concatenate((a[1],b[1],c[1],d[1]))])
data_len = len(data[0])


# In[6]:


plt.scatter(a[0],a[1])
plt.scatter(b[0],b[1])
plt.scatter(c[0],c[1])
plt.scatter(d[0],d[1])
plt.title("data view")
plt.show()


# In[7]:


# 스케일링


# In[8]:


def scale(data):
    if(np.max(data)-np.min(data)==0): # x/0 에러 처리
        return (data-np.min(data))/(1.0/np.max(data))
    else:
        return (data-np.min(data))/(np.max(data)-np.min(data))


# In[9]:


data = np.array([scale(data[0]),scale(data[1])])
plt.scatter(data[0], data[1])
plt.show()


# In[10]:


# 알고리즘


# In[11]:


# 파라미터
e = 0.05
n = 3


# In[12]:


# 서로간의 거리 2차원
distance = np.array([[np.sqrt((data[0,i]-data[0,j])**2 + (data[1,i]-data[1,j])**2) for j in range(data_len)] for i in range(data_len)])
a = 2
arr = []
for i in range(data_len):
    if distance[a,i] < e:
        arr += [data.T[i]]
arr = np.array(arr)
plt.axis([0, 1.1, 0, 1.1])
plt.scatter(data[0], data[1])
plt.scatter(arr.T[0],arr.T[1])
plt.scatter(data[0,a],data[1,a],c='r')
plt.show()


# In[13]:


# 거리계산
distance = np.array([[np.sqrt((data[0,i]-data[0,j])**2 + (data[1,i]-data[1,j])**2) for j in range(data_len)] for i in range(data_len)])

# 결과
core = []      #핵심벡터
outskirts = [] #외각벡터

# 이동
for i in range(data_len):
    neighbors = []
    
    # 데이터 탐색
    for j in range(data_len):
        # 범위안의 데이터 (이웃의 인덱스저장)
        if distance[i,j] <= e:
            neighbors += [j]
            
    # 본인이 핵심벡터일 경우
    if len(neighbors) >= n:
        core += [i]
        outskirts += neighbors
    
# i 점의 핵심백터
print(len(core))
plt.scatter(data[0], data[1],c='b',s=5)
plt.scatter([data[0,i] for i in outskirts],[data[1,i] for i in outskirts],c='r',s=5)
plt.show()


for i in core:
    circle = plt.Circle((data.T[i,0],data.T[i,1]), radius=e ,color='g')
    plt.gca().add_patch(circle)
plt.show()


# In[14]:


def debug(d1,d2):
    plt.axis([0, 1.1, 0, 1.1])
    plt.scatter(data[0], data[1])
    plt.scatter(arr.T[0],arr.T[1])
#     plt.scatter(data[0,i],data[1,i],c='r')
    plt.scatter(d1,d2,c='r')
    plt.show()


# In[15]:


print(distance)


# In[16]:


circle = plt.Circle((0,0), radius=0.5)
plt.gca().add_patch(circle)
plt.axis('scaled')
plt.show()


# In[17]:


print(data)

