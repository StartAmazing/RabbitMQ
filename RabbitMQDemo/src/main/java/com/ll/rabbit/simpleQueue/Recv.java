package com.ll.rabbit.simpleQueue;

import com.rabbitmq.client.*;

/**
 * @author LL
 * @date 2019/9/4
 * @description 消费者从队列中消费信息
 */
public class Recv {

    private final static String QUEUE_NAME="q_test_01";
    public static void main(String[] argv) throws Exception{
        //获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //同一时刻服务器只会发出一条消息给消费者
//        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //监听队列，false表示手动返回完成状态，true表示自动返回
        channel.basicConsume(QUEUE_NAME,true,consumer);

        //获取消息
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [y] Received ' " + message + " ' ");
            //休眠
            Thread.sleep(1000);
            //返回确认状态。注释表示使用自动确认模式
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }

    }




}
