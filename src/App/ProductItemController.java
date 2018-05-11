package App;

public class ProductItemController {

    ProductItem productItem;

    ProductItemController(ProductItem productItem){
        this.productItem = productItem;
    }

    public void increaseCount(){
       int count = Integer.parseInt(productItem.getLabelCount().getText().replaceAll("\\D+",""));

        if(count < 100) {
            productItem.getLabelCount().setText(Integer.toString(count + 1) + " " + productItem.product.getUnitSuffix());
        }
    }

    public void decreaseCount(){
        int count = Integer.parseInt(productItem.getLabelCount().getText().replaceAll("\\D+",""));

        if(count > 1) {
            productItem.getLabelCount().setText(Integer.toString(count - 1) + " " + productItem.product.getUnitSuffix());
        }
    }
}
