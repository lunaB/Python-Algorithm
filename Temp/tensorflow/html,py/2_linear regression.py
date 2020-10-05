
# coding: utf-8

# In[1]:

import tensorflow as tf


# In[2]:

# 학습 데이터
train_X = [1,2,3,4,5]
train_Y = [2,3,4,5,6]

# 텐서 변수
W = tf.Variable(tf.random_normal([1]), name='weight')
b = tf.Variable(tf.random_normal([1]), name='bias')
X = tf.placeholder(tf.float32, shape=[None])
Y = tf.placeholder(tf.float32, shape=[None])


# In[3]:

# 함수
hypothesis = X * W + b


# In[4]:

# 텐서 코스트합수
cost = tf.reduce_mean(tf.square(hypothesis - Y))


# In[5]:

#mininize 코스트 최소
optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.01)
train = optimizer.minimize(cost)


# In[6]:

sess = tf.Session()
init = tf.initialize_all_variables()
sess.run(init)

for step in range(2001):
    cost_val, W_val, b_val, _ =     sess.run([cost,W,b,train],feed_dict={X:train_X,Y:train_Y})
    if step % 200 == 0:
        print(step, cost_val, W_val, b_val)


# In[7]:

print("w=%s, b=%s" % (sess.run(W),sess.run(b))) #1,1


# In[8]:

print(sess.run(hypothesis, feed_dict={X:[10]})) #11

