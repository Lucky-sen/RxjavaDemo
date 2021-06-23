package com.example.rxjavademo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavademo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：admin016
 * 日期时间: 2021/6/22 9:33
 * 内容描述:
 */
public class RxTestActivity extends AppCompatActivity {

    private final static String TAG = "dss";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        errorReturnTest();
    }

    // create  操作符
    // 传入参数： OnSubscribe 对象
    // 当 Observable 被订阅时，OnSubscribe 的 call() 方法会自动被调用，即事件序列就会依照设定依次被触发
    // 即观察者会依次调用对应事件的复写方法从而响应事件
    // 从而实现由被观察者向观察者的事件传递 & 被观察者调用了观察者的回调方法 ，即观察者模式
    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

        //2. 在复写的subscribe（）里定义需要发送的事件
        @Override
        public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
            // 通过 ObservableEmitter类对象 产生 & 发送事件
            // ObservableEmitter类介绍
            // a. 定义：事件发射器
            // b. 作用：定义需要发送的事件 & 向观察者发送事件
            // 注：建议发送事件前检查观察者的isUnsubscribed状态，以便在没有观察者时，让Observable停止发射数据
            observableEmitter.onNext(1);
            observableEmitter.onNext(2);
            observableEmitter.onNext(3);
            observableEmitter.onComplete();
        }
    });

    /**
     * create操作符 链式调用
     */
    private void chainCreate() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("dss", "create开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d("dss", "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("dss", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("dss", "对Complete事件作出响应");
            }
        });
    }

    /**
     * just 同时传入多个操作符
     */
    private void chainJust() {
        // 1. 创建时传入整型1、2、3、4
        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
        Observable.just(1, 2, 3, 4).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("dss", "just开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d("dss", "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("dss", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("dss", "对Complete事件作出响应");
            }
        });
    }

    /**
     * fromArray 数组操作符
     */
    private void chainFromArray() {
        String[] array = {"hello", "nice", "welcome"};
        Observable.fromArray(array).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("dss", "fromArray开始采用subscribe连接");
            }

            @Override
            public void onNext(String value) {
                Log.d("dss", "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("dss", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("dss", "对Complete事件作出响应");
            }
        });
    }

    /**
     * fromIterable 列表操作符
     */
    private void chainFromIterable() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        Observable.fromIterable(list).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("dss", "fromIterable开始采用subscribe连接");
            }

            @Override
            public void onNext(String value) {
                Log.d("dss", "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("dss", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("dss", "对Complete事件作出响应");
            }
        });
    }

    // 下列方法一般用于测试使用


    // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
    Observable observable1 = Observable.empty();
    // 即观察者接收后会直接调用onCompleted（）


    // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
    // 可自定义异常
    Observable observable2 = Observable.error(new RuntimeException());
    // 即观察者接收后会直接调用onError（）

    // 该方法创建的被观察者对象发送事件的特点：不发送任何事件
    Observable observable3 = Observable.never();
    // 即观察者接收后什么都不调用

    /**
     * 延迟创建defer 操作符
     *
     */
    private void chainDefer(){
        Integer i = 10;
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>(){
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("dss", "defer开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d("dss", "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("dss", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("dss", "对Complete事件作出响应");
            }
        });
    }

    /**
     * 延迟发送事件，注意是先subscribe连接，然后5s之后发送事件
     * 延迟指定事件，发送一个0，一般用于检测
     */
    private void chainTimer(){
        // 该例子 = 延迟5s后，发送一个long类型数值
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    /**
     * 定时有序的发送事件
     * 其实直接退出了页面，也还是会继续发送
     */
    private void chainInterval(){
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        // 延迟发送一个0 ，并间隔 + 1
//        Observable.interval(5, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "开始采用subscribe连接");
//            }
//
//            @Override
//            public void onNext(Long value) {
//                Log.d(TAG, "接收到了事件"+ value  );
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "对Error事件作出响应");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "对Complete事件作出响应");
//            }
//        });
        // intervalRange()  同上面类似 只不过 ，指定的事件的次数
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(100, 5, 5, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "接收到了事件"+ value  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    /**
     * 连续发送 1个事件序列，可指定范围
     */
    private void chainRange(){
        Observable.range(10, 5).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件"+ value  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
        //与此差不多的还有个rangeLong（），区别在于rangeLong（）这个方法支持的数据类型是Long类型，而上面的是Integer类型
    }

    /**   ----------------  变换操作符  --------------- */

    /**
     *  map（Function<params1,params2></>）  params1->params2 类型转化
     */
    private void mapTest(){
        Observable.create(new ObservableOnSubscribe<Integer>(){

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(10);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "通过map()操作符转变为字符串"+integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,s );
            }
        });
   }

    /**
     * 拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序
     * flatMap 无序的事件拆分
     */
   private void concatMapTest(){
       Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
               emitter.onNext(1);
               emitter.onNext(2);
               emitter.onNext(3);
           }
       }).concatMap(new Function<Integer, ObservableSource<String>>() {
           @Override
           public ObservableSource<String> apply(Integer integer) throws Exception {
               final List<String> list = new ArrayList<>();
               for (int i = 0; i < 3; i++) {
                   list.add("我是事件 " + integer + "拆分后的子事件" + i);
                   // 通过concatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                   // 最终合并，再发送给被观察者
               }
               return Observable.fromIterable(list);
           }
       }).subscribe(new Consumer<String>() {
           @Override
           public void accept(String s) throws Exception {
               Log.d(TAG, s);
           }
       });
   }

    /**
     * 定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送
     * 54321事件
     * buffer过程 123，45  取123入缓存然后 事件接受123
     *            1，234，5  取234入缓存然后 事件接受324
     *            12，345，  取345入缓存然后 事件接受345
     *            123，45  取45入缓存然后 事件接受45
     *            1234，5  取5入缓存然后  事件接受5
      */
   private void bufferTest(){
       Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
               emitter.onNext(1);
               emitter.onNext(2);
               emitter.onNext(3);
               emitter.onNext(4);
               emitter.onNext(5);
               emitter.onComplete();
           }
           // 设置缓存区大小 & 步长
           // 缓存区大小 = 每次从被观察者中获取的事件数量
           // 步长 = 每次获取新事件的数量
       }).buffer(3,1)
               .subscribe(new Consumer<List<Integer>>() {
                   @Override
                   public void accept(List<Integer> integers) throws Exception {
                       for(Integer in : integers){
                           Log.d(TAG, "buffer" + in);
                       }
                   }
               });
   }

   /** -------------  组合/和平操作符  -------------- */
    /**
     * concat（） / concatArray（）
     * concat （）操作的数量 <=4  concatArray() > 4
     */
   private void concatTest(){
       Observable.concat(Observable.just(1,2),Observable.just(3,4),Observable.just(5,6),Observable.just(7,8))
               .subscribe(new Observer<Integer>() {
                   @Override
                   public void onSubscribe(Disposable d) {
                       Log.d(TAG, "开始采用subscribe连接");
                   }

                   @Override
                   public void onNext(Integer value) {
                        Log.d(TAG, "value" + value);
                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.d(TAG, "对Error事件作出响应");
                   }

                   @Override
                   public void onComplete() {
                       Log.d(TAG, "对Complete事件作出响应");
                   }
               });
   }

    /**
     * merge（） / mergeArray（）
     * 组合多个被观察者一起发送数据，合并后 按时间线并行执行
     * 即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个
     * 区别上述concat（）操作符：同样是组合多个被观察者一起发送数据，但concat（）操作符合并后是按发送顺序串行执行
     */
   private void mergeTest(){
       Observable.merge(Observable.intervalRange(1, 3, 1, 1, TimeUnit.SECONDS)
       ,Observable.intervalRange(10,3, 1, 1, TimeUnit.SECONDS)
       ,Observable.intervalRange(100,3, 1, 1, TimeUnit.SECONDS)
       ,Observable.intervalRange(1000, 3, 1, 1, TimeUnit.SECONDS)).subscribe(new Observer<Long>() {
           @Override
           public void onSubscribe(Disposable d) {
               Log.d(TAG, "开始采用subscribe连接");
           }

           @Override
           public void onNext(Long value) {
               Log.d(TAG, "value" + value);
           }

           @Override
           public void onError(Throwable e) {
               Log.d(TAG, "对Error事件作出响应");
           }

           @Override
           public void onComplete() {
               Log.d(TAG, "对Complete事件作出响应");
           }
       });
   }

    /**
     *  concat（） / concatArray（）
     *  当合并事件时，前面发送事件发送了error后面的不会执行
     */
   private void firstErrorTest(){
       Observable.concat(
               Observable.create(new ObservableOnSubscribe<Integer>() {
                   @Override
                   public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                       emitter.onNext(1);
                       emitter.onNext(2);
                       emitter.onNext(3);
                       emitter.onError(new NullPointerException()); // 发送Error事件，因为无使用concatDelayError，所以第2个Observable将不会发送事件
                       emitter.onComplete();
                   }
               }),
               //前面发送了事件onError  后面的事件不会发送
               Observable.just(4, 5, 6))
               .subscribe(new Observer<Integer>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }
                   @Override
                   public void onNext(Integer value) {
                       Log.d(TAG, "接收到了事件"+ value  );
                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.d(TAG, "对Error事件作出响应");
                   }

                   @Override
                   public void onComplete() {
                       Log.d(TAG, "对Complete事件作出响应");
                   }
               });
   }

    /**
     * concatArrayDelayError
     * 为了解决上面的多个观察者其中一个观察者
     * 第1个被观察者的Error事件将在第2个被观察者发送完事件后再继续发送
     */
   private void concatDelayError(){
       Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
               emitter.onNext(1);
               emitter.onNext(2);
               emitter.onNext(3);
               emitter.onError(new NullPointerException());
               emitter.onComplete();
           }
       }),Observable.just(10,20)).subscribe(new Observer<Integer>() {
           @Override
           public void onSubscribe(Disposable d) {

           }

           @Override
           public void onNext(Integer value) {
               Log.d(TAG, "接收到了事件"+ value  );
           }

           @Override
           public void onError(Throwable e) {
               Log.d(TAG, "对Error事件作出响应");
           }

           @Override
           public void onComplete() {
               Log.d(TAG, "对Complete事件作出响应");
           }
       });
   }

    /**
     * zip
     * 该类型的操作符主要是对多个被观察者中的事件进行合并处理。
     */
   private void zipTest(){
       //<-- 创建第1个被观察者 -->
               Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
               Log.d(TAG, "被观察者1发送了事件1");
               emitter.onNext(1);
               // 为了方便展示效果，所以在发送事件后加入2s的延迟
               Thread.sleep(1000);

               Log.d(TAG, "被观察者1发送了事件2");
               emitter.onNext(2);
               Thread.sleep(1000);

               Log.d(TAG, "被观察者1发送了事件3");
               emitter.onNext(3);
               Thread.sleep(1000);

               emitter.onComplete();
           }
       }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

       //<-- 创建第2个被观察者 -->
               Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
           @Override
           public void subscribe(ObservableEmitter<String> emitter) throws Exception {
               Log.d(TAG, "被观察者2发送了事件A");
               emitter.onNext("A");
               Thread.sleep(1000);

               Log.d(TAG, "被观察者2发送了事件B");
               emitter.onNext("B");
               Thread.sleep(1000);

               Log.d(TAG, "被观察者2发送了事件C");
               emitter.onNext("C");
               Thread.sleep(1000);

               Log.d(TAG, "被观察者2发送了事件D");
               emitter.onNext("D");
               Thread.sleep(1000);

               emitter.onComplete();
           }
       }).subscribeOn(Schedulers.newThread());// 设置被观察者2在工作线程2中工作
       // 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送

// 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
               Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
                   @Override
                   public String apply(Integer integer, String string) throws Exception {
                       return  integer + string;
                   }
               }).subscribe(new Observer<String>() {
                   @Override
                   public void onSubscribe(Disposable d) {
                       Log.d(TAG, "onSubscribe");
                   }

                   @Override
                   public void onNext(String value) {
                       Log.d(TAG, "最终接收到的事件 =  " + value);
                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.d(TAG, "onError");
                   }

                   @Override
                   public void onComplete() {
                       Log.d(TAG, "onComplete");
                   }
               });
   }
    /**
     *  combineLatest()
     *  当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据
     * 与Zip（）的区别：Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并
     *
     */

    /**
     * reduce（）
     * 把被观察者需要发送的事件聚合成1个事件 & 发送
     * 聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
     * 第一个被观察者1 * 第二个被观察者2 * 第三个被观察者 * 第四个被观察者
     */
    private void reduceTest(){
        Observable.just(1,2,3,4).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                Log.e(TAG, "本次计算的数据是： "+integer +" 乘 "+ integer2);
                return integer * integer2;
            }
        });
    }

    /**
     * collect（）
     * 将被观察者Observable发送的数据事件收集到一个数据结构里
     */
    private void collectTest(){
        Observable.just(1, 2, 3 ,4, 5, 6)
                .collect(
                        // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                        new Callable<ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> call() throws Exception {
                                return new ArrayList<>();
                            }
                            // 2. 对发送的数据进行收集
                        }, new BiConsumer<ArrayList<Integer>, Integer>() {
                            @Override
                            public void accept(ArrayList<Integer> list, Integer integer)
                                    throws Exception {
                                // 参数说明：list = 容器，integer = 后者数据
                                list.add(integer);
                                // 对发送的数据进行收集
                            }
                        }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(@NonNull ArrayList<Integer> s) throws Exception {
                Log.e(TAG, "本次发送的数据是： "+s);

            }
        });
    }

    /**
     * startWith（） / startWithArray（）
     * 发送事件前追加事件
     * 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
     *
     */
    private void startTest(){
        Observable.just(1, 2,3).startWith(1)
                               .startWithArray(4,5,6,7,8)
                               .subscribe(new Observer<Integer>(){
                                   @Override
                                   public void onSubscribe(Disposable d) {

                                   }

                                   @Override
                                   public void onNext(Integer value) {
                                       Log.d(TAG, "最终接收到的事件 =  " + value);
                                   }

                                   @Override
                                   public void onError(Throwable e) {

                                   }

                                   @Override
                                   public void onComplete() {

                                   }
                               });

        Observable.just(1, 2).startWith(Observable.just(3)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 统计发送事件数量 count()
     *  
     */
    private void countTest(){
        Observable.just(1, 2).count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long lo) throws Exception {
                Log.e(TAG, "发送的事件数量 =  "+lo);
            }
        });
    }

    /**
     * 使得被观察者延迟一段时间再发送事件
     */
    private void delayTest(){
        Observable.just(1).delay(2, TimeUnit.SECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 在事件发送 & 接收的整个生命周期过程中进行操作
     * 如发送事件前的初始化、发送事件后的回调请求等
     * do()
     */
    private void doTest(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Throwable("发生错误了"));
            }
        })
                // 1. 当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        Log.d(TAG, "doOnEach: " + integerNotification.getValue());
                    }
                })
                // 2. 执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doOnNext: " + integer);
                    }
                })
                // 3. 执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doAfterNext: " + integer);
                    }
                })
                // 4. Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doOnComplete: ");
                    }
                })
                // 5. Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "doOnError: " + throwable.getMessage());
                    }
                })
                // 6. 观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.e(TAG, "doOnSubscribe: ");
                    }
                })
                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doAfterTerminate: ");
                    }
                })
                // 8. 最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doFinally: ");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * 错误处理
     * onErrorReturn（）
     * 遇到错误时，发送1个特殊事件 & 正常终止
     * 可捕获在它之前发生的异常
     */
    private void errorReturnTest(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onError(new Throwable("发生错误了"));
                e.onNext(2);

            }
        })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        // 捕捉错误异常
                        Log.e(TAG, "在onErrorReturn处理了错误: "+throwable.toString() );

                        return 666;
                        // 发生错误事件后，发送一个"666"事件，最终正常结束
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * onErrorResumeNext（）  遇到错误时，发送1个新的Observable
     * onExceptionResumeNext（）   遇到错误时，发送1个新的Observable
     */
    private void onErrorResumeTest(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) throws Exception {

                        // 1. 捕捉错误异常
                        Log.e(TAG, "在onErrorReturn处理了错误: "+throwable.toString() );

                        // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                        return Observable.just(11,22);

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }


    /**
     *  retry()
     *  重试，即当出现错误时，让被观察者（Observable）重新发射数据
     * 接收到 onError（）时，重新订阅 & 发送事件
     * Throwable 和 Exception都可拦截
     */
    private void retryTest(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                .retry() // 遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


//<-- 2. retry（long time） -->
// 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
// 参数 = 重试次数
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })
                        .retry(3) // 设置重试次数 = 3次
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });

//<-- 3. retry（Predicate predicate） -->
// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
// 参数 = 判断逻辑
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })
                        // 拦截错误后，判断是否需要重新发送请求
                        .retry(new Predicate<Throwable>() {
                            @Override
                            public boolean test(@NonNull Throwable throwable) throws Exception {
                                // 捕获异常
                                Log.e(TAG, "retry错误: "+throwable.toString());

                                //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                                //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                                return true;
                            }
                        })
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });

//<--  4. retry（new BiPredicate<Integer, Throwable>） -->
// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
// 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })

                        // 拦截错误后，判断是否需要重新发送请求
                        .retry(new BiPredicate<Integer, Throwable>() {
                            @Override
                            public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                                // 捕获异常
                                Log.e(TAG, "异常错误 =  "+throwable.toString());

                                // 获取当前重试次数
                                Log.e(TAG, "当前重试次数 =  "+integer);

                                //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                                //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                                return true;
                            }
                        })
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });


//<-- 5. retry（long time,Predicate predicate） -->
// 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
// 参数 = 设置重试次数 & 判断逻辑
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })
                        // 拦截错误后，判断是否需要重新发送请求
                        .retry(3, new Predicate<Throwable>() {
                            @Override
                            public boolean test(@NonNull Throwable throwable) throws Exception {
                                // 捕获异常
                                Log.e(TAG, "retry错误: "+throwable.toString());

                                //返回false = 不重新重新发送数据 & 调用观察者的onError（）结束
                                //返回true = 重新发送请求（最多重新发送3次）
                                return true;
                            }
                        })
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });
    }

    /**
     *  repeatWhen()方法
     *  将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable），以此决定是否重新订阅 & 发送原来的 Observable
     *
     * 若新被观察者（Observable）返回1个Complete / Error事件，则不重新订阅 & 发送原来的 Observable
     * 若新被观察者（Observable）返回其余事件时，则重新订阅 & 发送原来的 Observable
     */
    private void repeatWhenTest(){
        Observable.just(1,2,4).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，这里我们使用的是flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable
                // 此处有2种情况：
                // 1. 若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                // 2. 若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 情况1：若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                        return Observable.empty();
                        // Observable.empty() = 发送Complete事件，但不会回调观察者的onComplete（）

                        // return Observable.error(new Throwable("不再重新订阅事件"));
                        // 返回Error事件 = 回调onError（）事件，并接收传过去的错误信息。

                        // 情况2：若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                        // return Observable.just(1);
                        // 仅仅是作为1个触发重新订阅被观察者的通知，发送的是什么数据并不重要，只要不是Complete（） /  Error（）事件
                    }
                });

            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应：" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

}
