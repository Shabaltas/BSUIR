#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <fcntl.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/shm.h>

#define AMOUNT_STR 1010
#define WRITE_STR 100
#define READ_STR 75
#define SIZE_STR 20
#define TRUE 1

int sid, count = 0;
int pipe1[2], pipe2[2];

union semun{
    int              val;
    struct semid_ds *buf;
    unsigned short  *array;
    struct seminfo  *__buf;
};

int get_current_time(){
    struct timeval te;
    gettimeofday(&te, NULL);
    return te.tv_usec/1000;
}

// (sid, 0)
int WaitSemaphore(int sem_id, int num, int sem_op){
    struct sembuf buf;
    buf.sem_op = -abs(sem_op);
    buf.sem_flg = IPC_NOWAIT;
    buf.sem_num = num;
    return semop(sem_id, &buf, 1);
}

int ReleaseSemaphore(int sem_id, int num, int sem_op){
    struct sembuf buf;
    buf.sem_op = abs(sem_op);
    buf.sem_flg = IPC_NOWAIT;
    buf.sem_num = num;
    return semop(sem_id, &buf, 1);
}

void handleParent(int signo){
	if(signo == SIGUSR1){
	    //printf("%d ended\n", ++count);
	    if (++count == 2){
		printf("END\n");
		close(pipe1[0]);
		close(pipe2[0]);
		/*int status;
                pid_t wpid;
                while ((wpid = wait(&status)) > 0);*/
    		exit(0);
	    }
    }
    if (signo == SIGUSR2){

    }
}

//index == 0 : child1(pipe1)
//index == 1 : child(2(pipe2)
void startChildWork(int index){
	char str[SIZE_STR];
	int pipe[2];
	if (index){
		pipe[0] = pipe2[0];
		pipe[1] = pipe2[1];
	}else{
		pipe[0] = pipe1[0];
		pipe[1] = pipe1[1];
	}
	close(pipe[0]);
	int currentAmountStr = 0;
	while (currentAmountStr < AMOUNT_STR){
		if (WaitSemaphore(sid, 0, 1) == 0){
			int countStr = 0;
			while (countStr < WRITE_STR){
				sprintf(str, "%d %d %d", currentAmountStr + countStr, getpid(), get_current_time());
				write(pipe[1], str, SIZE_STR);
				countStr++;
			}
		currentAmountStr += WRITE_STR;
		ReleaseSemaphore(sid, 0, 1);
		sleep(1);
		}
	}
	close(pipe[1]);
	sleep(index);
	//printf("%d EENNNNNNNDDDDDDDDDDDDDDDDDD\n", getpid());
	kill(getppid(), SIGUSR1);
	exit(0);
}

void handleChild1(int signo){
	if (signo == SIGUSR2){
		startChildWork(0);
	}
}

void handleChild2(int signo){
	if (signo == SIGUSR2){
		startChildWork(1);
	}
}

int main (){
  pid_t pid1, pid2;
  key_t key;
  union semun semunion;
  char str[SIZE_STR];

  if(pipe(pipe1) < 0){
   /* Если создать pipe не удалось, печатаем об этом сообщение и прекращаем работу */
     printf("Can\'t create pipe\n");
     exit(-1);
  }
  if(pipe(pipe2) < 0){
   /* Если создать pipe не удалось, печатаем об этом сообщение и прекращаем работу */
     printf("Can\'t create pipe\n");
     exit(-1);
  }

  key = ftok(".",getpid());
  if((sid = semget(key, 1, IPC_CREAT | 0666)) == -1)
  {
     printf("Semaphore get error!%s\n",strerror(errno));
     exit(1);
  }
  semunion.val = 2;
  semctl(sid, 0, SETVAL, semunion);

  struct sigaction actParent, actChild1, actChild2;

  memset(&actParent, 0, sizeof(actParent));
  actParent.sa_handler = handleParent;

  memset(&actChild1, 0, sizeof(actChild1));
  actChild1.sa_handler = handleChild1;

  memset(&actChild2, 0, sizeof(actChild2));
  actChild2.sa_handler = handleChild2;

  switch (pid1 = fork()){
  	case -1:
  		printf("Mistake fork\n");
  		exit(1);
    case 0:
    	sigaction(SIGUSR2, &actChild1, 0);
    	setpgid(0, getppid());
	while(1);
    	break;
    default:
    	switch (pid2 = fork()){
    		case -1:
    		  	printf("Mistake fork\n");
  				exit(1);
  			case 0:
  				sigaction(SIGUSR2, &actChild2, 0);
  				setpgid(0, getppid());
				while(1);
  				break;
  			default:
				sleep(1);
  				sigaction(SIGUSR1, &actParent, 0);
  				sigaction(SIGUSR2, &actParent, 0);
  				//block writing
  				close(pipe1[1]);
  				close(pipe2[1]);
  				setpgid(0, 0);
  				//to start the work
  				kill(0,SIGUSR2);
				sleep(1);
  				while (TRUE){
	  				if (WaitSemaphore(sid, 0, 2) == 0){
		  				printf("pipe1\n");
		  				int i;
						for(i=0;i<READ_STR;i++){
					        read(pipe1[0], str,SIZE_STR);
					        printf("%s\n",str);
					    }
					    printf("pipe2\n");
					    for(i=0;i<READ_STR;i++){
					        read(pipe2[0], str, SIZE_STR);
					        printf("%s\n",str);
					    }
						ReleaseSemaphore(sid, 0, 2);
				    	sleep(1);
					}
				}
    	}
  }
  return 0;
}
