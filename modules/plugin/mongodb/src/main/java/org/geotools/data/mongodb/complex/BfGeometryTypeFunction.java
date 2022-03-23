package org.geotools.data.mongodb.complex;

import static org.geotools.filter.capability.FunctionNameImpl.parameter;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import java.util.logging.Logger;
import org.geotools.filter.FunctionExpressionImpl;
import org.geotools.filter.capability.FunctionNameImpl;
import org.geotools.util.logging.Logging;
import org.opengis.filter.capability.FunctionName;
import org.opengis.filter.expression.VolatileFunction;

public final class BfGeometryTypeFunction extends FunctionExpressionImpl
        implements VolatileFunction {

    private static final Logger LOGGER = Logging.getLogger(BfGeometryTypeFunction.class);

    private static final FunctionName DEFINITION =
            new FunctionNameImpl(
                    "bfGeometryType", String.class, parameter("geometryType", String.class));

    public BfGeometryTypeFunction() {
        super(DEFINITION);
        LOGGER.warning("BfGeometryType initialised " + this.params);
    }

    public Object getMongoFilter(Object extraData) {
        LOGGER.warning("BfGeometryType getMongoFilter");
        String geometryField = (String) this.params.get(0).evaluate(extraData);

        DBObject dbo =
                BasicDBObjectBuilder.start()
                        .push("$elemMatch")
                        .add("type", (String) geometryField)
                        .get();

        LOGGER.warning("BfGeometryType getMongoFilter return " + dbo);

        return dbo;
    }

    protected BasicDBObject asDBObject(Object extraData) {
        if (extraData instanceof BasicDBObject) {
            return (BasicDBObject) extraData;
        }
        return new BasicDBObject();
    }
}
