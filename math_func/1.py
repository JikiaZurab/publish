from math import sin, pi, exp

def f(x): # Функция начальных условий
    return sin(pi * x)

def borders(u): # Заполнение краевых условий для t = 0
    for i in range(n):
        u[0][i] = f(i * delta_x)
        
    for i in range(t): # Заполнение краевых условий на концах стержня
        u[i][0] = 0
        u[i][n - 1] = 0

def explicit_scheme():
    if not (delta_t < 0.5 * delta_x * delta_x):
        print('Необходимо выбрать меньшее значение ∆t')
        exit()

    u = [[0] * n for _ in range(t)]
    borders(u)

    for i in range(1, t):
        for x in range(1, n - 1):
            u[i][x] = u[i - 1][x] + (delta_t / (delta_x * delta_x)) * (
                    u[i - 1][x + 1] - 2 * u[i - 1][x] + u[i - 1][x - 1])
    return u

if __name__ == '__main__':
    delta_x = 0.01
    delta_t = 0.00001
    t = int(input('t>>'))
    l = 1
    n = int(l / delta_x)  # количество точек сетки по оси OX
    scheme = explicit_scheme()
    print('Явная схема:')
    print(scheme[t - 1])
    exact = [(exp(-pi * pi * t * delta_t) * sin(pi * i * delta_x)) for i in range(n)]
    print('Точное решение:')
    print(exact)
