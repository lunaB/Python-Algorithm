import numpy as np
import matplotlib.pyplot as plt

# 최대적합도
MAX_FIT = 20

# 목표적합도
GOAL_FIT = 20

# 전체 알고리즘
class GeneticAlgorithm():

    # 세대 최대 갯수, 유전자 갯수, 유전자 길이, 보존 유전자 갯수, 돌연변이율
    def __init__(self, generationMax, geneCnt, geneLength, preservationGeneCnt, mutationRate=0.05):
        self.generationMax = generationMax
        self.geneCnt = geneCnt
        self.geneLength = geneLength
        self.preservationGeneCnt = preservationGeneCnt
        self.mutationRate = mutationRate

        self.level = 0

        self.generations = []
        self.generations.append(self.firstGeneration())


    # 초기세대와 초기유전자 설정
    def firstGeneration(self):
        return Generation(level=0, genes=[Gene(index=i, gene=np.random.randint(2, size=self.geneLength).tolist()) for i in range(self.geneCnt)])

    # 동작
    def run(self):
        while self.level < self.generationMax:
            print(self.generations[self.level])
            print("평균적합도", self.generations[self.level].fitness())
            print("최대적합유전자", self.generations[self.level].bestGene())
            print("최대적합유전자적합도", self.generations[self.level].bestGene().fitness())
            if self.generations[self.level].bestGene().fitness() <= GOAL_FIT:
                print("succeed")
                break
            nextGeneration = self.evolution()
            self.generations.append(nextGeneration)
            self.level += 1
    # 메인로직
    def debug(self):
        for level in range(self.generationMax):
            self.level = level
            # 세대번호
            print(self.generations[level])

            # 유전자모양
            print("유전자리스트")
            for i in range(self.geneCnt):
                print(self.generations[level].genes[i])

            # 세대 평균적합도
            print("평균적합도", self.generations[level].fitness())

            # 최대적합 유전자
            print("최대적합유전자", self.generations[level].bestGene())

            # 최대적합 유전자 적합도
            print("최대적합유전자적합도", self.generations[level].bestGene().fitness())

            # 보존유전자
            print("보존유전자")
            for i in range(self.preservationGeneCnt):
                print(self.generations[level].sortGenes()[i])
                print("적합도", self.generations[level].sortGenes()[i].fitness())

            if self.generations[level].bestGene().fitness() <= GOAL_FIT:
                print("succeed")
                break

            # 유전자생성
            nextGeneration = self.evolution()
            self.generations.append(nextGeneration)





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
    #
    # ...
    def createChild(self, parents):
        return self.generations[self.level].onePointCrossover(parents)

    # 진화
    # return next generation
    def evolution(self):
        nextGenes = []
        # 보존
        nextGenes += [self.generations[self.level].sortGenes()[i] for i in range(self.preservationGeneCnt)]

        # 부모선택 & 선별
        nextGenes += [self.createChild(self.selectParents()) for i in range(self.geneCnt - self.preservationGeneCnt)]

        # 재정렬
        for i, gene in enumerate(nextGenes):
            gene.index = i

        return Generation(level=self.level+1, genes=nextGenes)

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
        return np.mean([gene.fitness() for gene in self.genes])

    # 적합도순의 정렬
    def sortGenes(self):
        return sorted(self.genes, key=lambda x:x.fitness(), reverse=True)

    # 적합도가 가장높은 유전자
    def bestGene(self):
        return self.sortGenes()[0]

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
        point = np.random.randint(0, len(self.genes)-2)
        newGene = []
        for i in range(len(self.genes[0].gene)):
            if point >= i:
                newGene.append(parents[0].gene[i])
            else:
                newGene.append(parents[1].gene[i])
        return Gene(index=-1, gene=newGene)

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
        answer = [0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1]
        res = 0
        for i, ans in enumerate(answer):
            if self.gene[i] == ans:
                res += 1

        return abs(MAX_FIT - (MAX_FIT - res))


if __name__ == '__main__':
    ga = GeneticAlgorithm(generationMax=100, geneCnt=20, geneLength=20, preservationGeneCnt=5, mutationRate=0.05)
    ga.debug()
    # ga.run()