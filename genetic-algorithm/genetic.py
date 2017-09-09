import numpy as np
import matplotlib.pyplot as plt

# 최대적합도
MAX_FIT = 10

# 목표적합도
GOAL_FIT = 10

# 전체 알고리즘
class GeneticAlgorithm():

    # 세대 최대 갯수, 유전자 갯수, 유전자 길이, 보존 유전자 갯수, 돌연변이율
    def __init__(self, generationMax, geneCnt, geneLength, preservationGeneCnt, mutationRate=0.05):
        self.generationMax = generationMax
        self.geneCnt = geneCnt
        self.geneLength = geneLength
        self.preservationGeneCnt = preservationGeneCnt
        self.mutationRate = mutationRate

        self.generations = []
        self.generations.append(self.firstGeneration())


    # 초기세대와 초기유전자 설정
    def firstGeneration(self):
        return Generation(level=0,
                          genes=[Gene(index=i, gene=np.random.randint(2, size=self.geneLength).tolist()) for i in range(self.geneCnt)])

    # 메인로직
    def debug(self):
        for level in range(self.generationMax):

            # 세대번호
            print(self.generations[level])

            # 유전자모양
            # print("유전자리스트")
            # for i in range(self.geneCnt):
            #     print(self.generations[level].genes[i])

            # 세대 평균적합도
            print("평균적합도", self.generations[level].fitness())

            # 최대적합 유전자
            print("최대적합유전자", self.generations[level].sortGene()[0])

            # 최대적합 유전자 적합도
            print("최대적합유전자적합도", self.generations[level].sortGene()[0].fitness())

            # 보존유전자
            print("보존유전자")
            for i in range(self.preservationGeneCnt):
                print(self.generations[level].sortGene()[i])
                print("적합도", self.generations[level].sortGene()[i].fitness())

    def run(self):
        pass
# 세대
class Generation():

    # 세대의 번호, 전 세대에서 받은유전자 리스트
    def __init__(self, level, genes):
        self.level = level
        self.genes = genes

    # toString
    def __str__(self):
        return "<Generation level : %d>" % (self.level)

    # 세대의 평균 적합도
    def fitness(self):
        res = 0
        for gene in self.genes:
            res += gene.fitness()
        return res/len(self.genes)

    def sortGene(self):
        return sorted(self.genes, key=lambda x:x.fitness(), reverse=True)

    # 진화
    def evolution(self):

        return 0

    # 룰렛 휠
    def roulette(self):
        tuple()

# 유전자
class Gene():

    # 유전자의 번호, 유전자 배열
    def __init__(self, index, gene):
        self.index = index
        self.gene = gene

    # toString
    def __str__(self):
        return "<Gene[%d] gene : %s>" % (self.index, self.gene)

    # 적합도 함수정의
    def fitness(self):
        answer = [0, 1, 0, 1, 1, 1, 0, 0, 0, 1]
        res = 0
        for i, ans in enumerate(answer):
            if self.gene[i] == ans:
                res += 1

        return abs(MAX_FIT - (MAX_FIT - res))

ga = GeneticAlgorithm(generationMax=1, geneCnt=20, geneLength=10, preservationGeneCnt=5, mutationRate=0.05)
ga.debug()

