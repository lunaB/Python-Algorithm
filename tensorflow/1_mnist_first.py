
# coding: utf-8

# In[1]:

from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)


# In[2]:

import tensorflow as tf


# In[3]:

x = tf.placeholder(tf.float32,[None,784])
W = tf.Variable(tf.zeros([784,10]))
b = tf.Variable(tf.zeros([10]))
y = tf.nn.softmax(tf.matmul(x,W)+b)


# In[4]:

y_ = tf.placeholder(tf.float32,[None,10])
cross_entropy = tf.reduce_mean(-tf.reduce_sum(y_ * tf.log(y), reduction_indices=[1]))
train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)


# In[5]:

init = tf.initialize_all_variables()
sess = tf.Session()
sess.run(init)
for i in range(1000):
    batch_xs, batch_ys = mnist.train.next_batch(100)
    sess.run(train_step, feed_dict={x: batch_xs, y_: batch_ys})


# In[6]:

correct_prediction = tf.equal(tf.argmax(y,1),tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

#정확도 (일치율)
print("%s%%" % (sess.run(accuracy, feed_dict={x: mnist.test.images, y_:mnist.test.labels})))


# In[7]:

#직접 테스트 해볼 사진 번호
test_index = 16

output_label = sess.run(y, feed_dict={x:[mnist.test.images[test_index]]})[0]
print(output_label)

max_index = 0;
for i in range(10):
    if output_label[max_index] < output_label[i]:
        max_index = i
print("사진의 숫자는 %s 입니다." % (max_index))


# In[8]:

#출력 28*28 size
for i in range(len(mnist.test.images[test_index])):
    if mnist.test.images[test_index][i] > 0 :
        print("0"),
    else :
        print("."),
    if i%28 == 27 :
        print("")

