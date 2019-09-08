from math import sin, pi

if __name__ == '__main__':
    delta_x = 0.01
    delta_t = 0.001
    a = 1
    l = 1
    n = int(l / delta_x)  # Количество точек по оси OX
    t = int(input('t>>'))  # Интересующий нас момент времени
    u = [[0] * n for _ in range(t + 1)]  # Инициализация массива точек
    
    for i in range(n):# Заполнение начальных условий
        x = delta_x * i
        u[0][i] = 0.2 * i * delta_x * (1 - delta_x * i) * sin(pi * i * delta_x)
        u[1][i] = 0
        
    for i in range(t + 1): # Заполнение граничных условий
        u[t][0] = 0
        u[t][n - 1] = 0
        
    for i in range(2, t + 1):
        for j in range(1, n - 1):
            u[i][j] = 2 * u[i - 1][j] - u[i - 2][j] + a * a * ((delta_t * delta_t) / (delta_x * delta_x)) \
                      * (u[i - 1][j + 1] - 2 * u[i - 1][j] + u[i - 1][j - 1])

    for e in u[t]:
        print('%0.3f' % e, end=' ')
