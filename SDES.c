#include <stdio.h>

int l[4],r[4],keys[2][8],ct[8];

void sbox(int sip[],int p[],int sbno,int i) {

	int sbox[2][4][4]={1,0,3,2,3,2,1,0,0,2,1,3,3,1,3,2,0,1,2,3,2,0,1,3,3,0,1,0,2,1,0,3}; int rw,c,sop;
	
	rw = sip[3]+sip[0]*2;
	
	c = sip[2]+sip[1]*2;
	
	sop = sbox[sbno][rw][c];
	 
	for(;sop!=0;sop/=2)
	
	p[i--]=sop%2;

}

void cmp_fun(int round) {

	int EP[]={4,1,2,3,2,3,4,1},i,epd[8];
	
	int slip[4],srip[4];
	
	int p[4]={0},p4[]={2,4,3,1},np[4];
	
	for(i=0;i<8;i++) // Permutasi E/P
	
	epd[i]=r[EP[i]-1];
	
	for(i=0;i<8;i++)//Operasi XOR dari Key
	
	if(i<4)
	
	slip[i] = epd[i]^keys[round][i];
	
	else
	
	srip[i-4] = epd[i]^keys[round][i];
	
	sbox(slip,p,0,1);//Memanggil SBox 1, 0?>SBOX 1
	
	sbox(srip,p,1,3);//Memanggil SBox 1, 1?>SBOX 2
	
	for(i=0;i<4;i++) //Permutasi P4
	
	np[i]=p[p4[i]-1];
	
	for(i=0;i<4;i++)
	
	l[i] = l[i]^np[i];

}

void left_shift(int keyip[],int nob) {

	int t1,t2,i;
	
	while(nob>0) {
	
		t1=keyip[0],t2=keyip[5];
		
		for(i=0;i<9;i++)
		
		if(i<4)
		
		keyip[i] =keyip[i+1];
		
		else if(i>4)
		
		keyip[i] = keyip[i+1];
		
		keyip[4]=t1,keyip[9]=t2;
		
		nob--;

	}

}

void gen_keys() {

	int key[10],i,keyip[10];
	int p10[]={3,5,2,7,4,10,1,9,8,6},p8[]={6,3,7,4,8,5,10,9};
	printf("Masukkan kunci :");
	for(i=0;i<10;i++)
	scanf("%d", &key[i]);
	for(i=0;i<10;i++) // Permutasi P10
	keyip[i] = key[p10[i]-1];
	left_shift(keyip,1);
	printf("\nKey1 :");
	for(i=0;i<8;i++) //Permutasi P8 pada key1
	{	
		keys[0][i] = keyip[p8[i]-1];// Key1 dibuat
		printf("%d",keys[0][i]);
	}
	
	left_shift(keyip,2);// Membuat Key2 . .
	printf("\nKey2 :");
	for(i=0;i<8;i++)
	{
		keys[1][i] = keyip[p8[i]-1];// Key2 dibuat
		printf("%d",keys[1][i]);
	}
	
}
	
void En_De(int pt[],int c) {
	
	int ip[]={2,6,3,1,4,8,5,7},ipi[]={4,1,3,5,7,2,8,6},t[8],i; for(i=0;i<8;i++)// Permutasi pada bit masukan
	
	if(i<4)
	
	l[i]=pt[ip[i]-1];
	
	else
	
	r[i-4] = pt[ip[i]-1];
	
	cmp_fun(c);//Round 0+1 gunakan key 0+1
	
	for(i=0;i<4;i++) //Tukar left & right
	
	r[i]=l[i]+r[i],l[i]=r[i]-l[i],r[i]=r[i]-l[i];
	
	printf("\n\n");
	
	cmp_fun(!c);
	
	for(i=0;i<8;i++)
	
	if(i<4)	t[i]=l[i];
	
	else	t[i]=r[i-4];
	
	for(i=0;i<8;i++)
	
	ct[i] = t[ipi[i]-1];

}

void main() {

	int pt[8]={0},i;
	
	printf("Masukkan 8 bit plaintext:");
	
	for(i=0;i<8;i++)
	
	scanf("%d",&pt[i]);
	
	gen_keys(); // Membuat kunci key1 & key2
	
	En_De(pt,0);
	
	printf("\nCipher Text :");
	
	for(i=0;i<8;i++)
	
	printf("%d",ct[i]);
	 
	//Decrypting ? ? ?
	
	En_De(ct,1);
	
	printf("\nPlain Text (Setelah Decrypting):");
	
	for(i=0;i<8;i++)
	
	printf("%d",ct[i]);

}
