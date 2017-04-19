
# coding: utf-8

# In[1]:

import tensorflow as tf


# In[4]:

#좋지못한 예
x1_data = [73.,93.,89.,96.,73.]
x2_data = [80.,88.,91.,98.,66.]
x3_data = [75.,93.,90.,100.,90.]
y_data = [152.,185.,180.,196.,142.]

x1 = tf.placeholder(tf.float32)
x2 = tf.placeholder(tf.float32)
x3 = tf.placeholder(tf.float32)

y = tf.placeholder(tf.float32)

w1 = tf.Variable(tf.random_normal([1]),name='weight')
w2 = tf.Variable(tf.random_normal([1]),name='weight')
w3 = tf.Variable(tf.random_normal([1]),name='weight')

b = tf.Variable(tf.random_normal([1]),name='bias')

hypothesis = x1*w1 + x2*w2 + x3*w3 + b

cost = tf.reduce_mean(tf.square(hypothesis - y))

optimizer = tf.train.GradientDescentOptimizer(learning_rate=1e-5)
train = optimizer.minimize(cost)

sess = tf.Session()

sess.run(tf.initialize_all_variables())
for step in range(2001):
    cost_val, hy_val, _ = sess.run([cost, hypothesis, train],
                                  feed_dict={x1:x1_data, x2:x2_data, x3:x3_data, y:y_data})
    if step % 400 == 0:
        print(step, "Cost:" , cost_val, "\nPrediction:\n", hy_val)


# In[10]:

#좋은 예
#매트릭스 / 벡터 계산
x_data = [[73.,80.,75.],[93.,88.,93.],[89.,91.,90.],[96.,98.,100.],[73.,66.,90.]]
y_data = [[152.],[185.],[180.],[196.],[142.]]

x = tf.placeholder(tf.float32, shape=[None, 3])
y = tf.placeholder(tf.float32, shape=[None, 1])

w = tf.Variable(tf.random_normal([3,1]),name='weight')
b = tf.Variable(tf.random_normal([1]),name='bias')

hypothesis = tf.matmul(x, w) + b

cost = tf.reduce_mean(tf.square(hypothesis - y))

optimizer = tf.train.GradientDescentOptimizer(learning_rate=1e-5)
train = optimizer.minimize(cost)

sess = tf.Session()

sess.run(tf.initialize_all_variables())
for step in range(2001):
    cost_val, hy_val, _ = sess.run([cost, hypothesis, train],
                                  feed_dict={x:x_data, y:y_data})
    if step % 400 == 0:
        print(step, "Cost:" , cost_val, "\nPrediction:\n", hy_val)

