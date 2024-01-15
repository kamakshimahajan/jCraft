class Solution {
public:
    int getMax(int index, int direction, vector<int> &arr) {
        
        int res = INT_MIN;
        if(direction < 0) {
            for(int i = index; i >= 0; i--) 
                res = max(res, arr[i]);
        }
        else {
            for(int i = index; i < arr.size(); i++)
                res = max(res, arr[i]);
        }
        return res;
    }
    
    int minOperations(vector<int>& A, vector<int>& B) {
        
        if(A.size() == 1)   return 0;
        
        int count = 0;
        for(int i = A.size()-1; i >= 0; i--) {
            
            if(i == A.size()-1)  
            {
                // Find left max for both
                int aMax = getMax(i-1, -1, A);
                int bMax = getMax(i-1, -1, B);
                //
                if( (A[i] < aMax or B[i] < bMax) &&
                   (aMax > B[i] or bMax > B[i])) {
                    count++;
                    swap(A[i], B[i]);
                }
                else if(A[i] < aMax or B[i] < bMax) 
                    return -1;
            }
            else 
            {
                // Find right Max for both
                int aMax = getMax(i-1, 1, A);
                int bMax = getMax(i-1, 1, B);
   
                if( A[i] > aMax or B[i] > bMax) 
                {
                    if(aMax < B[i] or bMax < B[i]) {
                        
                    }
                    count++;
                    swap(A[i], B[i]);
                }
                else if (A[i] > aMax or B[i] > bMax)
                    return -1;
            }
        }
        return count;
    }
};