package quantumGrading;

import original.Tensor;

public class TensorWithRowNum {
	int rowNum;
	Tensor tensor;
	
	public TensorWithRowNum(Tensor tensor, int rowNum){
		this.rowNum = rowNum;
		this.tensor = tensor;
	}

}
