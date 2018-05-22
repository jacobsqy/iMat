package App;

public class ProductItemController {

    ProductItem productItem;

    ProductItemController(ProductItem productItem){
        this.productItem = productItem;
    }

    public void increaseCount(){
       int count = Integer.parseInt(productItem.getTxtCount().getText().replaceAll("\\D+",""));

        if(count < 100) {
            productItem.getTxtCount().setText(Integer.toString(count + 1));
        }
    }

    public void decreaseCount(){
        int count = Integer.parseInt(productItem.getTxtCount().getText().replaceAll("\\D+",""));

        if(count > 1) {
            productItem.getTxtCount().setText(Integer.toString(count - 1));
        }
    }
}
