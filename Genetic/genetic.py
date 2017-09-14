import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as pat

# 최대적합도
MAX_FIT = 20

# 목표적합도
GOAL_FIT = 20

# 돌연변이율
MUTATION_RATE = 0.01

# 전체 알고리즘
class GeneticAlgorithm():

    # 세대 최대 갯수, 유전자 갯수, 유전자 길이, 유전자범위, 보존 유전자 갯수, 돌연변이율
    def __init__(self, generationMax, geneCnt, geneLength, geneRange,preservationGeneCnt, mutationRate=0.05):
        self.generationMax = generationMax
        self.geneCnt = geneCnt
        self.geneLength = geneLength
        self.geneRange = geneRange
        self.preservationGeneCnt = preservationGeneCnt
        self.mutationRate = mutationRate

        self.level = 0

        self.generations = []
        self.generations.append(self.firstGeneration())


    # 초기세대와 초기유전자 설정
    def firstGeneration(self):
        return Generation(level=0,
                          genes=[Gene(index=i, gene=np.random.randint(self.geneRange, size=self.geneLength).tolist()) for i in range(self.geneCnt)],
                          geneRange=self.geneRange,
                          geneLength=self.geneLength)

    # 동작
    def run(self):
        while self.level < self.generationMax:
            if self.generations[self.level].bestGene().fitness() == GOAL_FIT:
                print("succeed")
                break
            nextGeneration = self.evolution()
            self.generations.append(nextGeneration)
            self.level += 1

    # 메인로직
    def debug(self):

        plotdata = []

        for level in range(self.generationMax):
            self.level = level
            # 세대번호
            print(self.generations[level])

            # 유전자모양
            print("유전자리스트")
            for i in range(self.geneCnt):
                print(self.generations[level].genes[i])
            #
            # # 세대 평균적합도
            # print("평균적합도", self.generations[level].fitness())
            #
            # # 최대적합 유전자
            # print("최대적합유전자", self.generations[level].bestGene())
            #
            # # 최대적합 유전자 적합도
            # print("최대적합유전자적합도", self.generations[level].bestGene().fitness())
            #
            # # 보존유전자
            # print("보존유전자")
            # for i in range(self.preservationGeneCnt):
            #     print(self.generations[level].sortGenes()[i])
            #     print("적합도", self.generations[level].sortGenes()[i].fitness())

            plotdata.append([self.generations[level].fitness(), self.generations[level].bestGene().fitness()])

            if self.generations[level].bestGene().fitness() == GOAL_FIT:
                print("succeed")
                break

            # 유전자생성
            nextGeneration = self.evolution()
            self.generations.append(nextGeneration)

        avg = pat.Patch(color='r', label='average')
        best = pat.Patch(color='b', label='best')

        plt.legend(handles=[best, avg])

        plt.title('genetic-algorithm')
        plt.xlabel('fitness')
        plt.ylabel('generation level')

        plt.plot([0,self.level],[GOAL_FIT,GOAL_FIT])
        plt.plot(np.array(plotdata).T[0],'r')
        plt.plot(np.array(plotdata).T[1],'b')

        plt.show()

    # 부모선택
    # {interface} return array [Gene(),]
    # roulette()
    # ...
    def selectParents(self):
        parents = [self.generations[self.level].roulette() for _ in range(2)]
        while parents[0] == parents[1]:
            parents[1] = self.generations[self.level].roulette()
        return parents

    # 자식생성
    # {interface} parents [Gene(),], return array [Gene(),]
    # onePointCrossover(parents)
    # multiPointCrossover(parents)
    # ...
    def createChild(self, parents):
        # return self.generations[self.level].onePointCrossover(parents)
        return self.generations[self.level].multiPointCrossover(parents)

    # 변이여부 선정
    # {interface} return boolean
    # standardMutation(rate)
    # ...
    def mutationSelection(self):
        return self.generations[self.level].standardMutation(MUTATION_RATE)


    # 변이생성
    # {interface} parents [Gene(),], return array [Gene(),]
    # createGene(rate)
    # ...
    def createMutation(self):
        return self.generations[self.level].createGene()

    # 진화
    # return next generation
    def evolution(self):
        nextGenes = []
        # 보존
        nextGenes += [self.generations[self.level].sortGenes()[i] for i in range(self.preservationGeneCnt)]

        # 부모선택 & 선별
        for i in range(self.geneCnt - self.preservationGeneCnt):
            if self.mutationSelection() == True:
                mutationGene = self.createMutation()
                nextGenes.append(mutationGene)
            else:
                nextGenes.append(self.createChild(self.selectParents()))

        # 재정렬
        for i, gene in enumerate(nextGenes):
            gene.index = i

        return Generation(level=self.level+1, genes=nextGenes, geneRange=self.geneRange, geneLength=self.geneLength)

# 세대
class Generation():

    # 세대의 번호, 전 세대에서 받은유전자 리스트
    def __init__(self, level, genes, geneRange, geneLength):
        self.level = level
        self.genes = genes
        self.geneRange = geneRange
        self.geneLength = geneLength

    # toString
    def __str__(self):
        return "<Generation level : %d>" % (self.level)

    # 세대의 평균 비용
    def cost(self):
        pass

    # 세대의 평균 적합도
    def fitness(self):
        return np.mean([gene.fitness() for gene in self.genes])

    # 적합도순의 정렬
    def sortGenes(self):
        return sorted(self.genes, key=lambda x:x.fitness(), reverse=True)

    # 적합도가 가장높은 유전자
    def bestGene(self):
        return self.sortGenes()[0]

    # 단일 유전자 생성 (돌연변이 이용)
    def createGene(self):
        return Gene(index=-1, gene=np.random.randint(2, size=len(self.genes[0].gene)).tolist())

    ##
    # 부모선택 함수
    ##

    # 선택압 추가해야함 (k)
    # 룰렛 휠
    def roulette(self):
        genesFit = [gene.fitness() for gene in self.genes]
        genesFit /= np.sum(genesFit)
        rand = np.random.rand()
        ntmp = 0
        for i, geneFit in enumerate(genesFit):
            ntmp += geneFit
            if rand <= ntmp:
                return self.genes[i]

    ##
    # 자녀생성
    ##

    # 1점교차
    # len(parents) == 2
    def onePointCrossover(self, parents):
        point = np.random.randint(0, len(self.genes[0].gene)-2)
        newGene = []
        for i in range(len(self.genes[0].gene[0].gene)):
            if point >= i:
                newGene.append(parents[0].gene[i])
            else:
                newGene.append(parents[1].gene[i])
        return Gene(index=-1, gene=newGene)

    # 다점교차 len
    # len(parents) == 2
    def multiPointCrossover(self, parents):
        point = [np.random.randint(0, len(self.genes[0].gene) - 2), np.random.randint(0, len(self.genes[0].gene) - 2)]
        newGene = []
        while point[0]==point[1]:
            point[1] = np.random.randint(0, len(self.genes[0].gene) - 2)

        for i in range(len(self.genes[0].gene)):
            if point[0] >= i or point[1] < i:
                newGene.append(parents[0].gene[i])
            else:
                newGene.append(parents[1].gene[i])
        return Gene(index=-1, gene=newGene)

    ##
    # 변이 확률 연산
    ##

    # 일반균등변이
    def standardMutation(self, rate):
        if np.random.rand() < rate:
            return True
        else:
            return False

    # 비용비례변이
    def costMutation(self):
        self.cost()
        pass

    ##
    # 변이 생성
    ##

    # 새로운 유전자 생성
    def createGene(self):
        return Gene(index=-1, gene=np.random.randint(self.geneRange, size=self.geneLength).tolist())

# 유전자
class Gene():

    # 유전자의 번호, 유전자 배열
    def __init__(self, index, gene):
        self.index = index
        self.gene = gene

    # toString
    def __str__(self):
        return "<Gene[%d] gene:%s>" % (self.index, self.gene)

    # 적합도 함수정의
    # interface
    def fitness(self):
        answer = [4, 0, 0, 1, 3, 3, 1, 0, 2, 2, 0, 4, 4, 2, 1, 2, 2, 2, 1, 3]
        res = 0
        for i, ans in enumerate(answer):
            if self.gene[i] == ans:
                res += 1

        return abs(MAX_FIT - (MAX_FIT - res))

    # 비용 함수정의
    # interface
    def cost(self):
        pass


if __name__ == '__main__':
    ga = GeneticAlgorithm(generationMax=500, geneCnt=30, geneLength=20, geneRange=5, preservationGeneCnt=2, mutationRate=0.05)
    ga.debug()
    # ga.run()
    # print(ga.generations[ga.level - 1].fitness())
