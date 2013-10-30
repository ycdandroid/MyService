#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <pthread.h>

#include <stdlib.h>
#include <sys/resource.h>

#ifdef __cplusplus
extern "C" {
#endif

#include <android/log.h>
#define LOG_TAG    "myndktest -- JNILOG" // 这个是自定义的LOG的标识
//#undef LOG // 取消默认的LOG
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG, __VA_ARGS__)
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG, __VA_ARGS__)
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG_TAG, __VA_ARGS__)

jstring Java_com_example_myservice_MainActivity_stringFromJNI(JNIEnv* env, jclass thiz){
	LOGD("logd---string from MyNdkTest.cpp");
	return (jstring)env->NewStringUTF("string from MyNdkTest.cpp");
}


void mytest(JNIEnv *env, jobject context);
void runProcess(const char* packageName, const char* serviceName);

//线程数
#define NUMTHREADS 1
//全局变量

JavaVM *g_jvm = NULL;
jobject g_obj = NULL;
jobject g_context = NULL;

void *thread_fun(void* arg){
	JNIEnv *env;
	jclass cls;
	jmethodID mid;
	jmethodID callback;
	jfieldID context;


	if(g_jvm->AttachCurrentThread(&env,NULL) != JNI_OK){
		LOGE("%s: AttachCurrentThread() failed", __FUNCTION__);
		return NULL;
	}

	cls = env->GetObjectClass(g_obj);
	if (cls == NULL) {
		LOGE("FindClass() Error.....");
		goto error;
	}

	mid = env->GetStaticMethodID(cls,"fromJNI","(I)V");
	if (mid == NULL) {
		LOGE("GetMethodID() Error.....");
		goto error;
	}
	env->CallStaticVoidMethod(cls, mid,(int)arg);

	callback  = env->GetStaticMethodID(cls, "callback", "(Landroid/content/Context;)V");
	if (callback == NULL) {
		LOGE("GetMethodID() callback Error.....");
		goto error;
	}

	context = env->GetStaticFieldID(cls, "mContext" ,"Landroid/content/Context;");
	if(context == NULL){
		LOGE("GetFieldID() mContext Error.....");
		goto error;
	}
	jstring strmsg ;
	strmsg = (jstring)env->NewStringUTF("string from MyNdkTest.cpp");
//	jobject mobject ;
//	mobject = env->GetStaticObjectField(cls, context);
//	env->CallStaticVoidMethod(cls, callback, mobject);
	env->CallStaticVoidMethod(cls, callback, g_context);

//	while(true){
//		LOGI("in while true=====");
//		sleep(2);
//	}
	 	 pid_t pid;
	    struct rlimit r;
	    int i;

//	    umask(0);

	    if((pid = fork()) < 0)
	    {
	    	LOGI("i am deamon==1");
	            perror("fork");
	            exit(0);
	    }
	    else if(pid != 0)
	    {
	    	LOGI("i am deamon==2");
//	            exit(0);
	    }

	    setsid();
	    LOGI("i am deamon==3");
	    if((pid = fork()) < 0)
	    {
	    	LOGI("i am deamon==4");
	            perror("fork");
	            exit(0);
	    }
	    else if(pid != 0)
	    {
	    	LOGI("i am deamon==5");
//	            exit(0);
	    }

	    chdir("/");
	    LOGI("i am deamon==6");
	    if(r.rlim_max == RLIM_INFINITY)
	    {
	            r.rlim_max = 1024;
	    }
//	    for(i = 0; i < r.rlim_max; i++)
//	    {
//	            close(i);
//	    }

	    while(1)
	    {
//			printf("i am deamon");
			LOGI("i am deamon sucess myservice====");
//			env->CallStaticVoidMethod(cls, callback, g_context);
			env->CallStaticVoidMethod(cls, mid,(int)arg);
			runProcess("com.example.MyService","com.example.myservice");
			sleep(2);
	    }



error:
	if (g_jvm->DetachCurrentThread() != JNI_OK) {
		LOGE("%s: DetachCurrentThread() failed", __FUNCTION__);
	}
	pthread_exit(0);
}

//使用am命令来启动android程序
void runProcess(const char* packageName, const char* serviceName)
{
	FILE *fp;
	char command[1024];
//	sprintf(command, "am start -n %s/%s", packageName, serviceName);
	//sprintf(command, "am startservice -n %s/%s", packageName, serviceName);
	sprintf(command, "am startservice -a com.example.myservice");
	LOGI("run cmd: %s\n", command);
	if ((fp = popen(command,"r")) == NULL)
	{
		LOGE("popen failed\n");
		exit(1);
	}else{
		LOGI("popen sucess\n");
	}

	pclose(fp);
}


void mytest(JNIEnv *env, jobject ctx){
	 LOGI("i am deamon==test");
	 jclass Intent;
	 jmethodID constructId;
	 jobject mIntent;
	 Intent = env->FindClass("android.content.Intent");
	 constructId = env->GetMethodID(Intent,"<init>","(Ljava/lang/String;)V");
	 jstring action ;
	 action = (jstring)env->NewStringUTF("com.example.myservice");
	 mIntent = env->NewObject(Intent, constructId, action);
	 if (mIntent == NULL) {
		 LOGE("i am deamon====NewObject");
		 exit(0);
	}

	 jclass Context;
	 jmethodID startService;
	 Context = env->GetObjectClass(g_context);
	 startService = env->GetMethodID(Context, "startService","(Landroid/content/Intent;)Landroid/content/ComponentName;");
	 if (startService == NULL) {
		 LOGE("i am deamon====startService");
		 exit(0);
	}
	 env->CallVoidMethod(Context, startService, mIntent);
	 LOGD("i am deamon====startService");

}


JNIEXPORT void Java_com_example_myservice_MainActivity_mainThread(JNIEnv* env, jobject obj){
	int i;
	pthread_t pt[NUMTHREADS];

	for (i = 0; i < NUMTHREADS; i++) {
		pthread_create(&pt[i], NULL, &thread_fun, (void *)i);
	}
}

JNIEXPORT void Java_com_example_myservice_MainActivity_setJNIEnv(JNIEnv* env, jobject obj){
	env->GetJavaVM(&g_jvm);
	g_obj = env->NewGlobalRef(obj);
}

JNIEXPORT void Java_com_example_myservice_MainActivity_setContext(JNIEnv* env, jobject obj, jobject jCtxObj){
	g_context = env->NewGlobalRef(jCtxObj);
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved){
	JNIEnv* env = NULL;
	jint result = -1;

	if (vm->GetEnv((void **)&env, JNI_VERSION_1_4) != JNI_OK) {
		LOGE("GetEnv failed!");
		return result;
	}
	 return JNI_VERSION_1_4;
}







#ifdef __cplusplus
}
#endif
