package App;

public class ProductItemController {

    ProductItem productItem;

    ProductItemController(ProductItem productItem){
        this.productItem = productItem;
    }

    public void increaseCount(){
        int count= 0;
        if(!productItem.getTxtCount().getText().isEmpty()) {
            count = Integer.parseInt(productItem.getTxtCount().getText().replaceAll("\\D+",""));
        } else {
            count = Integer.parseInt(productItem.textOldValue().replaceAll("\\D+",""));
        }

       if(count < 100) {
           productItem.getTxtCount().setText(Integer.toString(count + 1));
       }
    }

    public void decreaseCount(){
        int count= 0;
        if(!productItem.getTxtCount().getText().isEmpty()) {
            count = Integer.parseInt(productItem.getTxtCount().getText().replaceAll("\\D+",""));
        } else {
            count = Integer.parseInt(productItem.textOldValue().replaceAll("\\D+",""));
        }


        if(count > 1) {
            productItem.getTxtCount().setText(Integer.toString(count - 1));
        }
    }
}
