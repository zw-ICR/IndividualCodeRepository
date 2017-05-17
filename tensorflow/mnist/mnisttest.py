from tensorflow.examples.tutorials.mnist import input_data
import tensorflow as tf

#自动从tensorflow官网下载测试数据
mnist = input_data.read_data_sets('MNIST_data', one_hot=True)


#构建y = wx + b模型
x = tf.placeholder("float", [None,784])
W = tf.Variable(tf.zeros([784,10]))
b = tf.Variable(tf.zeros([10]))

y = tf.nn.softmax(tf.matmul(x,W) + b,name="output")
y_ = tf.placeholder("float", [None,10])

#计算交叉熵
cross_entropy = -tf.reduce_sum(y_*tf.log(y))

#加入优化器
train_step = tf.train.GradientDescentOptimizer(0.01).minimize(cross_entropy)

init = tf.initialize_all_variables()

with tf.Session() as sess:
    sess.run(init)
    for i in range(1000):
        #训练样本
        batch_xs,batch_ys = mnist.train.next_batch(100)
        sess.run(train_step,feed_dict={x:batch_xs,y_:batch_ys})
        
        #使用真实值测试训练效果
        correct_prediction = tf.equal(tf.argmax(y,1), tf.arg_max(y_,1))
        accuracy = tf.reduce_mean(tf.cast(correct_prediction,"float"))
        print(sess.run(accuracy,feed_dict={x:mnist.test.images,y_:mnist.test.labels}))
    
#     output_graph_def = tf.graph_util.convert_variables_to_constants(sess,sess.graph_def,output_node_names=["output"])
#     with tf.gfile.FastGFile("c:/minist.pb",mode='wb') as f:
#         f.write(output_graph_def.node)

