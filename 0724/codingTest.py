n = int(input())
apple_num = int(input())
arr = [[0 for _ in range(n)] for _ in range(n)]
for _ in range(apple_num):
    a,b= map(int,input().split())
    arr[a-1][b-1]= 2
change_num = int(input())
direction = ['N' fr _ in range(10000)]
for _ in range(change_num):
    input_str = input()
    a,b = input_str.split()
    direction[int(a)-1] = b
cur_direction = [(0,1),(1,0),(0,-1),(-1,0)]
answer = 0
cur_col = 0
cur_row = 0
arr[0][0]=1
cur_dir = 0


def move_head(cur_dir,prev_col,prev_row):
    cur_col = prev_col + cur_direction[cur_dir][0]
    cur_row = prev_row + cur_direction[cur_dir][1]
    return cur_col,cur_row

q=[(cur_col,cur_row)]
while True:

    cur_col,cur_row = move_head(cur_dir,cur_col,cur_row)
    answer+=1
    if cur_col>=n or cur_row >=n or cur_col <0 or cur_row <0 :
        break
    if arr[cur_col][cur_row] == 1:
        break

    # 사과가 없는 경우
    if arr[cur_col][cur_row] != 2:
        x,y = q.pop(0)
        arr[x][y]=0

    arr[cur_col][cur_row] = 1
    q.append((cur_col,cur_row))

    if direction[answer-1] == 'D':
        cur_dir= (cur_dir + 1) % 4
    elif direction[answer-1] =='L':
        cur_dir= (cur_dir + 3) % 4



print(answer)
