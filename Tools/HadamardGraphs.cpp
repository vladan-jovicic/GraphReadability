#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <vector>
#include <cmath>
#include <fstream>

using namespace std;

struct graph {
	vector<int>adj;
	vector<int> code;
};

graph g[200];
int k, cnt=0, n, iterCnt = 0;

void backtrack(vector<int> in)
{
	cout << "Iter: " << iterCnt << endl;
	iterCnt++;
	if(in.size() == k)
	{
		bool found = false;
		for(int i=0; i<in.size(); i++)
		{
			if(in[i] != 0)
			{
				found = true;
				break;
			}
		}
		if(found)
		{
			for(int i=0; i<in.size(); i++)
			{
				g[cnt].code.push_back(in[i]);
				g[n+cnt].code.push_back(in[i]);
			}
			cnt++;
		}
		return;
	}
	
	in.push_back(0);
	backtrack(in);
	in.pop_back();
	in.push_back(1);
	backtrack(in);
	in.pop_back();
	return;
	
}

int main()
{
	int m=0;
	k = 4;
	n = pow(2, k)-1;
	vector<int> v;
	backtrack(v);
	for(int i=0; i<n; i++)
	{
		for(int j=0; j<g[i].code.size(); j++)
		{
			cout << g[i].code[j];
		}
		cout << "     ";
		for(int j=0; j<g[n+i].code.size(); j++)
		{
			cout << g[n+i].code[j];
		}
		cout << endl;
	}
	for(int i=0; i<n;i++)
	{
		for(int j=0; j<n; j++)
		{
			//check the inner product
			int sum = 0;
			for(int t = 0; t<k; t++)
			{
				sum += g[i].code[t]*g[n+j].code[t];
			}
			if(sum % 2 == 1)
			{
				m++;
				g[i].adj.push_back(n+j);
			}
		}
	}
	ofstream out("outGraph.in");
	out << 2*n << " " << m << endl;
	for(int i=0; i<n; i++)
	{
		for(int j=0; j<g[i].adj.size(); j++)
		{
			out << i << " " << g[i].adj[j] << endl;
		}
	}
	out.close();
	return 0;
}
