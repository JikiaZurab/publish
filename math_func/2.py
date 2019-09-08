from math import sin, pi, exp

def solve(a, b):
    m = len(b)  
    x = [0.0] * m
    alpha = [0.0] * m
    beta = [0.0] * m
    alpha[0] = -(a[0][1] / a[0][0])     # Первые прогоночные коэффициенты
    beta[0] = b[0] / a[0][0]
    
    for i in range(1, m - 1): # Вычисление прогоночных коэффициентов для уравнений со второго до предпоследнего
        alpha[i] = -(a[i][i + 1] / (alpha[i - 1] * a[i][i - 1] + a[i][i]))
        beta[i] = (b[i] - beta[i - 1] * a[i][i - 1]) / (alpha[i - 1] * a[i][i - 1] + a[i][i])
    alpha[m - 1] = 0 # Вычисление прогоночныз коэффициентов для последнего уравнения
    beta[m - 1] = (b[m - 1] - beta[m - 2] * a[m - 1][m - 2]) / (alpha[m - 2] * a[m - 1][m - 2] + a[m - 1][m - 1])
    x[m - 1] = beta[m - 1] # Обратный ход - вычисление неизвестных
    for i in range(m - 2, -1, -1):
        x[i] = alpha[i] * x[i + 1] + beta[i]
    return x

def f(x): # Функция начальных условий
    return sin(pi * x)

def borders(u):
    for i in range(n): # Заполнение краевых условий для t = 0
        u[0][i] = f(i * delta_x)
        
    for i in range(t): # Заполнение краевых условий на концах стержня
        u[i][0] = 0
        u[i][n - 1] = 0

def implicit_scheme():
    u = [[0] * n for _ in range(t)]
    borders(u)
    a = -delta_t # Создание трехдиагональной матрицы
    b = delta_x * delta_x + 2 * delta_t
    c = -delta_t
    tridiagonal = [[0] * (n - 2) for _ in range(n - 2)]
    tridiagonal[0][0] = b
    tridiagonal[0][1] = c

    for i in range(1, n - 3):
        tridiagonal[i][i - 1] = a
        tridiagonal[i][i] = b
        tridiagonal[i][i + 1] = c
    tridiagonal[n - 3][n - 4] = a
    tridiagonal[n - 3][n - 3] = b

    for i in range(1, t):
        right = list(map(lambda e: e * delta_x * delta_x, u[i - 1][1:n - 1]))  # Вектор правых частей
        right[0] -= u[i][0]
        right[n - 3] += u[i][n - 1]
        ut = solve(tridiagonal, right)
        for j in range(1, n - 1):
            u[i][j] = ut[j - 1]
    return u

if __name__ == '__main__':
    delta_x = 0.01
    delta_t = 0.00001
    t = int(input('t>>'))
    l = 1
    n = int(l / delta_x)  # количество точек сетки по оси OX
    scheme = implicit_scheme()
    print('Неявная схема:')
    print(scheme[t - 1])
    exact = [(exp(-pi * pi * t * delta_t) * sin(pi * i * delta_x)) for i in range(n)]
    print('Точное решение')
    print(exact)
