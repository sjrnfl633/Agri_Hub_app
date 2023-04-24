package com.example.agri_hub.umachine

data class UmachineItem(

    val id: Double,
    val device: String,
    val m_address: String
)

/***
 * 1	umachine_num	int default 1	총 보유 제어기 수량
2	umachine1	char(8)	1번 제어기 내부번호 (예:smed0001)
3	umachine1_add	varchar(100)	1번제어기 설치 위치
4	umachine2	char(8)	2번 제어기 번호
5	umachine2_add	varchar(100)	2번제어기 설치 위치
6	umachine3	char(8)	3번 제어기 번호
7	umachine3_add	varchar(100)	3번제어기설치 위치
8	umachine4	char(8)	4번 제어기 번호
9	umachine4_add	varchar(100)	4번제어기설치 위치
 */